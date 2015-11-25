package koala.weibo.support.http;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.net.ssl.HttpsURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import koala.weibo.support.utils.Utility;

/**
 * Created by taoxj on 15-11-24.
 */
public class JavaHttpUtility {
    private static final int CONNECT_TIMEOUT = 10 * 1000 ;
    private static final int READ_TIMEOUT = 10 * 1000;
    private static final int DOWNLOAD_CONNECT_TIMEOUT = 15 * 1000;
    private static final int DOWNLOAD_READ_TIMEOUT = 60 * 1000;
    private static final int UPLOAD_CONNECT_TIMEOUT = 15 * 1000;
    private static final int UPLOAD_READ_TIMEOUT = 5 * 60 * 1000;



    public String executeNormalTask(String url,HttpMethod method,Map<String,String> param){
        switch(method){
            case Post:
                return doPost(url,param);

            case Get:
                return doGet(url, param);
        }
        return "";
    }
    public String doPost(String urlAddress ,Map<String,String> param){
        try {
            URL url = new URL(urlAddress);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            connection.setReadTimeout(READ_TIMEOUT);
            connection.connect();
            DataOutputStream output = new DataOutputStream(connection.getOutputStream());
            Log.e("koala", Utility.encodeUrl(param));
            output.write(Utility.encodeUrl(param).getBytes());
            output.flush();
            output.close();
            return handleResponse(connection);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
    public String doGet(String urlAddress,Map<String,String> param){
           StringBuilder sb =new StringBuilder(urlAddress);
           sb.append("?").append(Utility.encodeUrl(param));
        try {
            URL url = new URL(sb.toString());
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            urlConnection.setDoOutput(false);
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
            urlConnection.setReadTimeout(READ_TIMEOUT);
            urlConnection.setRequestProperty("Charset", "UTF-8");
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            urlConnection.connect();

            return handleResponse(urlConnection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String handleResponse(HttpsURLConnection urlConnection){
        int status = 0;
        String message = "";
        try {
            status = urlConnection.getResponseCode();
        } catch (IOException e) {
            urlConnection.disconnect();
            Log.e("koala", "disconnect");
        }
        if(status != HttpsURLConnection.HTTP_OK){
            Log.e("koala", "error=========" + message);
        }
        return readResult(urlConnection);
    }
    public String readResult(HttpsURLConnection connection){
        try {
            InputStream is =  connection.getInputStream();
            String content_code = connection.getContentEncoding();
            if(!TextUtils.isEmpty(content_code) && content_code.equals("gzip")){
                is =new GZIPInputStream(is);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();
            String line;
            while((line = reader.readLine()) != null){
                sb.append(line);
            }
            Log.e("koala",sb.toString());
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }
        return "";
    }
}
