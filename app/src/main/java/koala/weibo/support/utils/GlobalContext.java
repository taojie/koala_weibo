package koala.weibo.support.utils;

import android.app.Application;

import koala.weibo.bean.AccountBean;

/**
 * Created by taoxj on 15-11-24.
 */
public class GlobalContext extends Application {
    private static GlobalContext context = null;
    private AccountBean accountBean;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static GlobalContext getInstance(){
        return context;
    }

    public void saveAccountBean(AccountBean account){
        this.accountBean = account;
    }

    public AccountBean getAccountBean(){
        return accountBean;
    }
}
