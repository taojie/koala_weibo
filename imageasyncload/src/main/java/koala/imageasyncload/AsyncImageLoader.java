package koala.imageasyncload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by taoxj on 15-12-15.
 */
public class AsyncImageLoader {

    class AsyncImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView image;
        String url;

        public AsyncImageTask(ImageView image, String url) {
            this.image = image;
            this.url = url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return getBitmapFromUrl(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(image.getTag().equals(url)){
                image.setImageBitmap(bitmap);
            }
        }
    }


    public void showImageByAsync(ImageView image, String url) {
        new AsyncImageTask(image,url).execute(url);
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


}
