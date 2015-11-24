package koala.weibo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;

import koala.weibo.dao.URLHepler;
import koala.weibo.dao.login.OauthAccessTokenDao;
import koala.weibo.support.utils.Utility;


public class OauthActivity extends Activity {

    private WebView webView;
    private String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oauth);
        getActionBar().setTitle(getString(R.string.oauth_title));
        getActionBar().setDisplayHomeAsUpEnabled(false);
        webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new WeiboWebViewClient());

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_oauth, menu);
        refresh();
        return true;
    }

    public void refresh() {
        webView.clearView();
        webView.loadUrl(getWeibooAuthUrl());
    }

    public String getWeibooAuthUrl() {
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("client_id", "3745794535");
        param.put("redirect_uri", URLHepler.DIRECT_URL);
        param.put("display", "mobile");
        return URLHepler.URL_OAUTH2_ACCESS_AUTHORIZE + "?" + Utility.encodeUrl(param);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class WeiboWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (url.startsWith(URLHepler.DIRECT_URL)) {
                handleRedirectUrl(view, url);
            }
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;//this will run in the local app
        }
    }

    public void handleRedirectUrl(WebView view, String url) {
        Log.e("koala",url + "===========enrter handle");
        Bundle values = Utility.parseUrl(url);
        code= values.getString("code");
        Log.e("koala", code + "============code");
        new OAuthAccessTokenTask().execute(code);
    }

    private static class OAuthAccessTokenTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
           new OauthAccessTokenDao(params[0]).login();
            return null;
        }
    }

}
