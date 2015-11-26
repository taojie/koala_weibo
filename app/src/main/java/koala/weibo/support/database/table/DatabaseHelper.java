package koala.weibo.support.database.table;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import koala.weibo.support.utils.GlobalContext;

/**
 * Created by taoxj on 15-11-25.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper dbHelper = null;
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "koala_weibo.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    static final String CREATE_ACCOUNT_TABLE_SQL = "create table " + AccountTable.TABLE_NAME
            + "("
            + AccountTable.UID + " integer primary key,"
            + AccountTable.TOKEN + " text,"
            + AccountTable.EXPIRES_IN + " text,"
            + AccountTable.INFOJSON + " text"
            + ");";

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("koala","on database create");
        db.execSQL(CREATE_ACCOUNT_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public static synchronized DatabaseHelper getInstance() {
        if (dbHelper == null) {
            dbHelper = new DatabaseHelper(GlobalContext.getInstance());
        }
        return dbHelper;
    }

}
