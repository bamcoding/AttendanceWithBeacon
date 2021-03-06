package net.gondor.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static int DB_VERSION = 12;
    protected Context context;

    public DBHelper(Context paramContext) {
        this(paramContext, "ktds_univ_user.db", null, DB_VERSION);
        context = paramContext;
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer createTable = new StringBuffer();
        createTable.append(" CREATE TABLE USERS ( ");
        createTable.append(" USER_ID TEXT PRIMARY KEY, ");
        createTable.append(" USER_PASSWORD TEXT, ");
        createTable.append(" USER_NAME ) ");
        db.execSQL(createTable.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion >= 11) {
            db.execSQL("DROP TABLE IF EXISTS USERS");
            onCreate(db);
        }
    }
}