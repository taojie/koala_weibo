package koala.weibo.support.http;

import java.net.HttpURLConnection;
import java.util.Map;

/**
 * Created by taoxj on 15-11-24.
 */
public class HttpUtility {
    private static HttpUtility httpUtility = new HttpUtility();

    public static HttpUtility getInstance(){
        return httpUtility;
    }
    public String executeNormalTask(String url,HttpMethod method,Map<String,String> param){
           return new JavaHttpUtility().executeNormalTask(url,method,param);
    }
}
