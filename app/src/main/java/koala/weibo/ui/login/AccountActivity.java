package koala.weibo.ui.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import koala.weibo.R;
import koala.weibo.bean.AccountBean;
import koala.weibo.support.database.AccountDBTask;
import koala.weibo.ui.main.MainTimeLineActivity;


public class AccountActivity extends Activity implements LoaderManager.LoaderCallbacks<List<AccountBean>>{
    public static final int ADD_ACCOUNT_REQUEST_CODE = 1;
    public static final int LOAD_ID = 0;
    public ListView listView;
    public AccountAdapter listAdapter;
    private List<AccountBean> accountList = new ArrayList<AccountBean>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setTitle(getString(R.string.app_name));
        listView = (ListView) findViewById(R.id.account_list);
        listAdapter = new AccountAdapter();
        listView.setOnItemClickListener(new AccountItemClickListener());
        listView.setAdapter(listAdapter);
        getLoaderManager().initLoader(LOAD_ID,null,this);
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

    @Override
    public Loader<List<AccountBean>> onCreateLoader(int id, Bundle args) {
        Log.e("koala","create loader=====");
        return new AccountDBLoader(AccountActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<List<AccountBean>> loader, List<AccountBean> data) {
        Log.e("koala","loader finished=====");
         accountList = data;
        Log.e("koala",accountList.size() + "");
         listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<AccountBean>> loader) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ADD_ACCOUNT_REQUEST_CODE && resultCode == RESULT_OK){
            refresh();
        }
    }

    public void refresh(){
        getLoaderManager().getLoader(LOAD_ID).forceLoad();
    }

    private static class AccountDBLoader extends AsyncTaskLoader<List<AccountBean>>{

        public AccountDBLoader(Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }

        @Override
        public List<AccountBean> loadInBackground() {
            return AccountDBTask.getAccountList();
        }
    }

    private class AccountAdapter extends BaseAdapter{
        private int defaultBG;
        private int selectedBG;

        public AccountAdapter(){
            defaultBG = getResources().getColor(R.color.transparent);
            selectedBG = Color.BLUE;
        }
        @Override
        public int getCount() {
            return accountList.size();
        }

        @Override
        public Object getItem(int position) {
            return accountList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();
            if(view == null  || view.getTag() == null){
                LayoutInflater inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.account_list_item_layout,parent,false);
                holder = new ViewHolder();
                holder.avatar = (ImageView) view.findViewById(R.id.accout_image_avatar);
                holder.account_name = (TextView) view.findViewById(R.id.account_name);

                view.setTag(holder);
            }else{
                holder = (ViewHolder) view.getTag();
            }
            view.setBackgroundColor(defaultBG);
            if(listView.getCheckedItemPositions().get(position)){
                view.setBackgroundColor(selectedBG);
            }
            holder.account_name.setText(accountList.get(position).getUserBean().getScreen_name());
            //获取账户头像

            return view;
        }
    }
  //  static class
    class ViewHolder{
      ImageView avatar;
      TextView account_name;
  }

    private class AccountItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent=   MainTimeLineActivity.newIntent(accountList.get(position));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
}
