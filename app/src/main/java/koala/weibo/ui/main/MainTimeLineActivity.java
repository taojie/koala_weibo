package koala.weibo.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import koala.weibo.R;
import koala.weibo.bean.AccountBean;
import koala.weibo.support.utils.BundleArgsExtra;
import koala.weibo.support.utils.GlobalContext;

public class MainTimeLineActivity extends Activity {
    private AccountBean accountBean;

    public static Intent newIntent(AccountBean account){
        Intent intent = new Intent();
        intent.putExtra(BundleArgsExtra.ACCOUNT_RXTRA, account);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            accountBean = savedInstanceState.getParcelable(BundleArgsExtra.ACCOUNT_RXTRA);
        }else{
            Intent intent = getIntent();
            accountBean = intent.getParcelableExtra(BundleArgsExtra.ACCOUNT_RXTRA);
        }
        //application存储account
        if (accountBean == null) {

        }
        GlobalContext.getInstance().saveAccountBean(accountBean);
        buildInterface(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_time_line, menu);
        return true;
    }

    private void buildInterface(Bundle savedInstanceState){
         getActionBar().setTitle(accountBean.getUserBean().getScreen_name());
         getWindow().setBackgroundDrawable(null);
         setContentView(R.layout.menu_right);
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
}
