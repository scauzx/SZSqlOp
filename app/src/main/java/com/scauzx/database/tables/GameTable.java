package com.scauzx.database.tables;


import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2017/12/6.
 */

public class GameTable {
    public static String TABLE_GAME_NAME = "table_game";
    public static String ATTRIBUTE_GAME_ID = "game_id";
    public static String ATTRIBUTE_GAME_NAME = "gamename";
    public static String ATTRIBUTE_GAME_DESC = "gamedesc";



    /**
     * 建表语句
     */
    public static String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_GAME_NAME + " ("
            + ATTRIBUTE_GAME_ID + " integer primary key autoincrement, "
            + ATTRIBUTE_GAME_NAME + " varchar(20), "
            + ATTRIBUTE_GAME_DESC + " text not null"
            + ")";


    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVesion) {
        if (oldVersion == 1) {
            db.execSQL(TABLE_CREATE);
        }
    }

}
