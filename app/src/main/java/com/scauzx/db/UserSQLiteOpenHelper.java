package com.scauzx.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.scauzx.utils.TableUtils;


/**
 *
 * @author scauzx
 * @date 2017/12/5
 */

public class UserSQLiteOpenHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "user_info.db";
    public static final int DATABASE_VERSION = 1;

    public UserSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableUser = "CREATE TABLE IF NOT EXISTS " + TableUtils.TABLE_USER_NAME + " (" + TableUtils.ATTRIBUTE_USER_ID + " integer primary key autoincrement, " + TableUtils.ATTRIBUTE_USER_NAME +
                " varchar(20), " + TableUtils.ATTRIBUTE_USER_PASSWORD + " varchar(20)" + ")";
        db.execSQL(tableUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public synchronized void close() {
        super.close();
    }
}
