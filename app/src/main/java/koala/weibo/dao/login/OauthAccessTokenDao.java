package koala.weibo.dao.login;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import koala.weibo.bean.AccountBean;
import koala.weibo.bean.UserBean;
import koala.weibo.dao.URLHepler;
import koala.weibo.support.http.HttpMethod;
import koala.weibo.support.http.HttpUtility;

/**
 * Created by taoxj on 15-11-24.
 * get access_token
 */
public class OauthAccessTokenDao {

    private String client_id;
    private String client_secret;
    private String grant_type;
    private String code;
    private String redirect_uri;

    public OauthAccessTokenDao(String code) {
        this.code = code;
    }

    public AccountBean login() {
        Map<String, String> param = new HashMap<String, String>();
        param.put("client_id", "3745794535");
        param.put("client_secret", "1aea9b8ee867b4bd8fd42a3e7aa35b13");
        param.put("grant_type", "authorization_code");
        param.put("code", code);
        param.put("redirect_uri", URLHepler.DIRECT_URL);

        String jsonData = HttpUtility.getInstance().executeNormalTask(URLHepler.GET_ACCESS_TOKEN, HttpMethod.Post, param);
        try {
            JSONObject data = new JSONObject(jsonData);
            String token = data.optString("access_token", "");
            String uid = data.optString("uid", "");
            String expires_in = data.optString("expires_in", "");

            AccountBean account = new AccountBean();
            account.setToken(token);
            account.setExpires_in(System.currentTimeMillis() + Long.valueOf(expires_in) * 1000);

            Map<String, String> userInfoParam = new HashMap<String, String>();
            userInfoParam.put("access_token", token);
            userInfoParam.put("uid", uid);
            return getUserInfo(userInfoParam,account);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AccountBean getUserInfo(Map<String, String> param,AccountBean account) {
        String jsonData = HttpUtility.getInstance().executeNormalTask(URLHepler.GET_USER_INFO, HttpMethod.Get, param);
        Log.e("koala","last=====" + jsonData);
        Gson gson = new Gson();
        UserBean user;
        user = gson.fromJson(jsonData, UserBean.class);
        Log.e("koala", user.toString());
        account.setUserBean(user);
        return account;
    }
}
