package com.scauzx.database;

import android.database.sqlite.SQLiteDatabase;

import com.scauzx.utils.AppUtils;

/**
 *
 * @author scauzx
 * @date 2017/12/5
 */

public class UserDataBaseFactory {
    private static UserSQLiteOpenHelper sDbHelper = null;

    public static SQLiteDatabase getDataBase() {
        if (sDbHelper == null) {
            synchronized (UserDataBaseFactory.class) {
                if (sDbHelper == null) {
                    sDbHelper = new UserSQLiteOpenHelper(AppUtils.getContext());
                }
            }
        }
        return sDbHelper.getWritableDatabase();
    }
}
