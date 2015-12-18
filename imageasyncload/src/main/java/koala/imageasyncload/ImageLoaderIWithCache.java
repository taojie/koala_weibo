package koala.imageasyncload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by taoxj on 15-12-15.
 */
public class ImageLoaderIWithCache {

    private LruCache<String, Bitmap> lruCache = null;
    private Set<AsyncImageTask> mTask;
    private ListView mListView;
    public ImageLoaderIWithCache(ListView listView) {
        mListView = listView;
        long size = Runtime.getRuntime().maxMemory();
        int cacheSize = (int) (size / 10);
        lruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };

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
            Bitmap bitmap = getBitmapFromUrl(params[0]);
            if (bitmap != null) {
                addImageToMemory(params[0], bitmap);
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
            new AsyncImageTask(url).execute(url);
        } else {
            image.setImageBitmap(bitmap);
        }

    }

    public Bitmap getBitmapFromUrl(String urlString) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(urlString);
            InputStream is = url.openStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bitmap = BitmapFactory.decodeStream(bis);
            is.close();
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
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
