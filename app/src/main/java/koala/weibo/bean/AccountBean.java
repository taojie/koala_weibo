package koala.weibo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by taoxj on 15-11-25.
 */
public class AccountBean implements Parcelable {
    private String token;
    private long expires_in;
    private UserBean userBean;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
         dest.writeString(token);
        dest.writeLong(expires_in);
        dest.writeParcelable(userBean,flags);
    }
}
