package koala.weibo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;


public class AccountActivity extends Activity {
    public static final int ADD_ACCOUNT_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setTitle(getString(R.string.app_name));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar_menu_accountactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.add_account_menu:
                showAddAccountDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    public void showAddAccountDialog(){
        final ArrayList<Class> activityList = new ArrayList<Class>();
        ArrayList<String> itemListName = new ArrayList<String>();
        activityList.add(OauthActivity.class);
        itemListName.add(getString(R.string.oauth_dialog));
        new AlertDialog.Builder(this).setItems(itemListName.toArray(new String[0]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setClass(AccountActivity.this,activityList.get(which));
                startActivityForResult(intent,ADD_ACCOUNT_REQUEST_CODE);
            }
        }).show();
    }
}
