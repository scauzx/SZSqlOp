package com.scauzx.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.scauzx.database.tables.GameTable;
import com.scauzx.database.tables.UserTable;


/**
 *
 * @author scauzx
 * @date 2017/12/5
 */

public class UserSQLiteOpenHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "user_info.db";
    public static final int DATABASE_VERSION = 2;

    public UserSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        UserTable.onCreate(db);
        GameTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        UserTable.onUpgrade(db,oldVersion,newVersion);
        GameTable.onUpgrade(db,oldVersion,newVersion);
    }

    @Override
    public synchronized void close() {
        super.close();
    }
}
