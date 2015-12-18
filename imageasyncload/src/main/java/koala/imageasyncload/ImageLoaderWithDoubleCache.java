package koala.imageasyncload;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import libcore.io.DiskLruCache;

/**
 * Created by taoxj on 15-12-17.
 */
public class ImageLoaderWithDoubleCache {

    private LruCache<String, Bitmap> lruCache = null;
    private DiskLruCache mDiskLruCache = null;
    private Set<AsyncImageTask> mTask;
    private ListView mListView;

    public ImageLoaderWithDoubleCache(Context context,ListView listView){
        mListView = listView;
        long size = Runtime.getRuntime().maxMemory();
        int cacheSize = (int) (size / 10);
        lruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };


        File file = getFileCache(context,"disk_cache");
        if(!file.exists()){
            file.mkdirs();
        }
        Log.e("koala", file.getAbsolutePath());
        try {
            mDiskLruCache = DiskLruCache.open(file,1,1,10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mTask = new HashSet<AsyncImageTask>();
    }

    public void addImageToMemory(String url, Bitmap bitmap) {
        if (lruCache.get(url) == null) {
            lruCache.put(url, bitmap);
        }
    }

    public Bitmap getBitmapFromMemory(String url) {
        return lruCache.get(url);
    }

    class AsyncImageTask extends AsyncTask<String, Void, Bitmap> {
        String url;

        public AsyncImageTask(String url) {
            this.url = url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            DiskLruCache.Snapshot snapshot;
            String key = Utils.toMD5String(url);
            FileInputStream fileInputStream = null;
            FileDescriptor fileDescriptor = null;
            Bitmap bitmap = null;
            //���ж϶����������Ƿ���
            try {
                snapshot = mDiskLruCache.get(params[0]);
                if(snapshot == null){//����������û��
                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    if(editor != null){
                       OutputStream os =  editor.newOutputStream(0);
                        if(getBitmapFromUrl(url,os)){
                            editor.commit();
                        }else{
                            editor.abort();
                        }
                    }
                    snapshot = mDiskLruCache.get(key);
                }
                if(snapshot!= null){
                    fileInputStream = (FileInputStream) snapshot.getInputStream(0);
                    fileDescriptor = fileInputStream.getFD();
                }
                if(fileDescriptor != null) {
                    bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                }
                if(bitmap != null){
                    addImageToMemory(url,bitmap); //��ӵ�һ������
                }
                return  bitmap;

            } catch (IOException e) {
                e.printStackTrace();
            }


            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView image = (ImageView) mListView.findViewWithTag(url);
            if (image != null && bitmap != null) {
                image.setImageBitmap(bitmap);
            }
            mTask.remove(this);
        }
    }


    public void showImageByAsync(ImageView image, String url) {
        Bitmap bitmap;
        bitmap = getBitmapFromMemory(url);
        if (bitmap == null) {
            image.setImageResource(R.mipmap.ic_launcher);
        } else {
            image.setImageBitmap(bitmap);
        }

    }

    public boolean getBitmapFromUrl(String urlString,OutputStream os) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            URL url = new URL(urlString);
            InputStream is = url.openStream();
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(os);

            int b;
            while((b = bis.read()) != -1){
                bos.write(b);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

                try {
                    if(bis != null){
                    bis.close();
                    }
                    if(bos != null){
                        bos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        return false;
     }

    public File getFileCache(Context context,String disk_dir){
       String cachePath;
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()){
            cachePath = context.getExternalCacheDir().getPath();
        }else{
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + disk_dir);
    }

    public void loadImage(int start, int end) {
        for (int i = start; i < end; i++) {
            String url = ImageUtil.IMAGE_URLS[i];
            Bitmap bitmap = getBitmapFromMemory(url);
            if (bitmap == null) {
                AsyncImageTask task = new AsyncImageTask(url);
                task.execute(url);
                mTask.add(task);
            } else {
                ImageView image = (ImageView) mListView.findViewWithTag(url);
                image.setImageBitmap(bitmap);
            }
        }


    }

    public void cancleAllTasks(){
        if(mTask!= null) {
            for (AsyncImageTask task : mTask) {
                task.cancel(false);
            }
        }
    }

}
