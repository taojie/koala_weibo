package koala.weibo.support.asyncdrawable;

import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import koala.weibo.bean.UserBean;

/**
 * Created by taoxj on 15-11-27.
 */
public class TimeLineBitmapDownloader {
    private static TimeLineBitmapDownloader downloader ;
    private Handler handler;
    private static final Object lock= new Object();

    public TimeLineBitmapDownloader(Handler handler){
        this.handler = handler;
    }
    public static TimeLineBitmapDownloader getInstance(){
       synchronized (lock){
           if(downloader == null){
               downloader = new TimeLineBitmapDownloader(new Handler(Looper.getMainLooper()));
           }
           return downloader;
       }
    }

    public void downloadAvatar(ImageView view,UserBean userBean,boolean isFling){
       if(userBean == null){
           return;
       }
        String url = userBean.getProfile_image_url();
        displayImageView(view,url,isFling,false);
    }

    public void displayImageView(ImageView view,String urlKey,boolean isFling,boolean isMultiView){
        view.clearAnimation();
        if(!shouldReloadPicture(view,urlKey)){
            return ;
        }

    }
    public boolean shouldReloadPicture(ImageView view,String urlKey){
        if(urlKey.equals(view.getTag())
                && view.getDrawable()!= null
                && view.getDrawable() instanceof BitmapDrawable
                && ((BitmapDrawable)view.getDrawable())!= null
                && ((BitmapDrawable) view.getDrawable()).getBitmap() != null){
            return false;
        }else{
            view.setTag(null);
            return true;
        }
    }
}
