package com.scauzx.database.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.scauzx.database.UserDataBaseFactory;
import com.scauzx.database.tables.GameTable;
import com.scauzx.database.tables.UserTable;

/**
 *
 * @author Administrator
 * @date 2017/12/6
 */

public class UserProvider extends ContentProvider{
    public static final String AUTHORITY = "com.scauzx.db.UserProvider";
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");
    public static final Uri GAME_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/game");
    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String TAG = UserProvider.class.getSimpleName();
    private Context mContext;
    private SQLiteDatabase mDataBase;
    private static final int CODE_USER = 0;
    private static final int CODE_GAME = 1;


    /**
     * 注册URI分发
     */
    static {
        mUriMatcher.addURI(AUTHORITY, "user", CODE_USER);
        mUriMatcher.addURI(AUTHORITY, "game", CODE_GAME);
    }


    public String getTableName(Uri uri) {
        String tableName = null;
        switch (mUriMatcher.match(uri)) {
            case CODE_USER:
                tableName = UserTable.TABLE_USER_NAME;
                break;
            case CODE_GAME:
                tableName = GameTable.TABLE_GAME_NAME;
                break;
            default:
                break;
        }
        return tableName;

    }

    @Override
    public boolean onCreate() {
        initProvider();
        return false;
    }

    private void initProvider() {
        mContext = getContext();
        mDataBase = UserDataBaseFactory.getDataBase();
    }


    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String tableName  = getTableName(uri);
        if (TextUtils.isEmpty(tableName)) {
            return null;
        }
        Log.i(TAG, "query: tableName = " + tableName);
        Cursor cursor = mDataBase.query(tableName, projection, selection, selectionArgs, null, sortOrder, null);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String tableName  = getTableName(uri);
        if (TextUtils.isEmpty(tableName)) {
            return null;
        }
        mDataBase.insert(tableName, null, values);
        mContext.getContentResolver().notifyChange(uri, null);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName  = getTableName(uri);
        if (TextUtils.isEmpty(tableName)) {
            return 0;
        }
        int result = mDataBase.delete(tableName, selection, selectionArgs);
        mContext.getContentResolver().notifyChange(uri, null);
        return result;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName  = getTableName(uri);
        if (TextUtils.isEmpty(tableName)) {
            return 0;
        }
        int result = mDataBase.update(tableName, values, selection, selectionArgs);
        mContext.getContentResolver().notifyChange(uri, null);
        return result;
    }
}
