package koala.weibo.support.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import koala.weibo.ui.login.OauthActivity;
import koala.weibo.bean.AccountBean;
import koala.weibo.bean.UserBean;
import koala.weibo.support.database.table.AccountTable;
import koala.weibo.support.database.table.DatabaseHelper;

/**
 * Created by taoxj on 15-11-25.
 */
public class AccountDBTask {

    public static SQLiteDatabase getWsd(){
        DatabaseHelper dbHepler = DatabaseHelper.getInstance();
        return dbHepler.getWritableDatabase();
    }

    public static SQLiteDatabase getRsd(){
        return DatabaseHelper.getInstance().getReadableDatabase();
    }

    public static List<AccountBean> getAccountList(){
        Log.e("koala", "get account list=====");
        List<AccountBean> accountList = new ArrayList<AccountBean>();
        String sql = "select * from " + AccountTable.TABLE_NAME;
        Cursor cursor =  getWsd().rawQuery(sql,null);

        while(cursor.moveToNext()){
            AccountBean account = new AccountBean();
            int colid = cursor.getColumnIndex(AccountTable.TOKEN);
            account.setToken(cursor.getString(colid));

            colid = cursor.getColumnIndex(AccountTable.EXPIRES_IN);
            account.setExpires_in(Long.valueOf(cursor.getString(colid)));

            colid = cursor.getColumnIndex(AccountTable.INFOJSON);
            String userData = cursor.getString(colid);
            Gson gson = new Gson();
            UserBean userBean = gson.fromJson(userData, UserBean.class);
            account.setUserBean(userBean);
            accountList.add(account);
        }

        return accountList;
    }
    public static OauthActivity.DBTask addOrUpdateAccount(AccountBean account){
        ContentValues values= new ContentValues();

        values.put(AccountTable.UID,account.getUserBean().getId());
        values.put(AccountTable.TOKEN,account.getToken());
        values.put(AccountTable.EXPIRES_IN, account.getExpires_in());

        Gson gson = new Gson();
        String userBean = gson.toJson(account.getUserBean());
        values.put(AccountTable.INFOJSON, userBean);
        Cursor cursor = getWsd().query(AccountTable.TABLE_NAME,null,AccountTable.UID + "=?",new String[]{account.getUserBean().getId()},null,null,null);
        if(cursor != null && cursor.getCount() > 0){
            Log.e("koala", "update========");
             getWsd().update(AccountTable.TABLE_NAME, values, AccountTable.UID + "=?", new String[]{account.getUserBean().getId()});
             return OauthActivity.DBTask.UPDATE_SUCCESSFULLY;
        }else{
            Log.e("koala", "add========");
             getWsd().insert(AccountTable.TABLE_NAME,AccountTable.UID,values);
            return OauthActivity.DBTask.ADD_SUCCESSFULLY;
        }
    }
}
