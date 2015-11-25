package koala.weibo.support.utils;

import android.app.Application;

/**
 * Created by taoxj on 15-11-24.
 */
public class GlobalContext extends Application {
    private static GlobalContext context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static GlobalContext getInstance(){
        return context;
    }

}
