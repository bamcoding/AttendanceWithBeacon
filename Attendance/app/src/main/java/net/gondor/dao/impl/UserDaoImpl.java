package net.gondor.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import net.gondor.dao.DBHelper;
import net.gondor.vo.UserVO;

/**
 * Created by mcjan on 2016-11-28.
 */

public class UserDaoImpl extends DBHelper {

    public UserDaoImpl(Context paramContext) {
        super(paramContext);
    }


    public void saveUserAccount(UserVO user) {
        ContentValues cv = new ContentValues();
        cv.put("USER_ID", user.getUserId());
        cv.put("USER_PASSWORD", user.getPassword());
        cv.put("USER_NAME", user.getUserName());
        SQLiteDatabase db = getWritableDatabase();
        db.insert("USERS", null, cv);
        db.close();
    }

    public UserVO getUserModel() {

        String query = "SELECT USER_ID, USER_PASSWORD FROM USERS";
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        UserVO user = null;

        if (cursor.moveToNext()) {
            user = new UserVO();
            user.setUserId(cursor.getString(0));
            user.setPassword(cursor.getString(1));
        }
        return user;
    }

}