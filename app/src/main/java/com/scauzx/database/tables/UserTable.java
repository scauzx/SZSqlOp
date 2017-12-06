package com.scauzx.database.tables;

import android.database.sqlite.SQLiteDatabase;

/**
 *
 * @author scauzx
 * @date 2017/12/5
 */

public class UserTable {
    public static String TABLE_USER_NAME = "table_user";
    public static String ATTRIBUTE_USER_ID = "user_id";
    public static String ATTRIBUTE_USER_NAME = "username";
    public static String ATTRIBUTE_USER_PASSWORD = "password";

    /**
     * 建表语句
     */
    public static String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER_NAME + " ("
            + ATTRIBUTE_USER_ID + " integer primary key autoincrement, "
            + ATTRIBUTE_USER_NAME + " varchar(20), "
            + ATTRIBUTE_USER_PASSWORD + " varchar(20)"
            + ")";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
