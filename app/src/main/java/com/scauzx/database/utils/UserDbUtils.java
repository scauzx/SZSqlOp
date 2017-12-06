package com.scauzx.database.utils;


import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;
import com.scauzx.database.content.UserProvider;
import com.scauzx.database.tables.UserTable;
import com.scauzx.models.Result;
import com.scauzx.models.User;
import com.scauzx.utils.ListUtils;
import com.scauzx.utils.ResourceUtils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sacuzx
 * @date 2017/12/6
 */

public class UserDbUtils {

    private static final String TAG = UserDbUtils.class.getSimpleName();
    private static Object mLock = new Object();

    public static List<User> getUserInfo() {
        List<User> users = new ArrayList<>();
        synchronized (mLock) {
            Cursor cursor =  ResourceUtils.getContentResolver().query(UserProvider.USER_CONTENT_URI,null,null,null,null,null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    User user = new User();
                    user.id = cursor.getInt(cursor.getColumnIndex(UserTable.ATTRIBUTE_USER_ID));
                    user.username = cursor.getString(cursor.getColumnIndex(UserTable.ATTRIBUTE_USER_NAME));
                    user.password = cursor.getString(cursor.getColumnIndex(UserTable.ATTRIBUTE_USER_PASSWORD));
                    users.add(user);
                }
                cursor.close();
            }
        }
        Log.i(TAG, "getUserInfo: users = " + users.toString());
        return users;
    }

    public static void addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(UserTable.ATTRIBUTE_USER_NAME, user.username);
        values.put(UserTable.ATTRIBUTE_USER_PASSWORD, user.password);
        try {
            synchronized (mLock) {
                ResourceUtils.getContentResolver().insert(UserProvider.USER_CONTENT_URI, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void bulkAddUser(List<User> users) {
        if (ListUtils.isEmpty(users)) {
            return;
        }
        if (users.size() == 1) {
            addUser(users.get(0));
            return;
        }
        ContentValues[] values = new ContentValues[users.size()];
        int i = 0;
        for(User user : users) {
            ContentValues value = new ContentValues();
            value.put(UserTable.ATTRIBUTE_USER_NAME, user.username);
            value.put(UserTable.ATTRIBUTE_USER_PASSWORD, user.password);
            values[i++] = value;
        }
        synchronized (mLock) {
            try{
                ResourceUtils.getContentResolver().bulkInsert(UserProvider.USER_CONTENT_URI, values);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteUser(String username) {
        if (TextUtils.isEmpty(username)) {
            return;
        }
        deleteUsersByUserName(new String[] {username});

    }

    public static void deleteUsersByUserName(String[] username) {
        if (username == null || username.length == 0) {
            return;
        }
        String whereClause = UserTable.ATTRIBUTE_USER_NAME + " =? ";
        synchronized (mLock) {
            try {
                ResourceUtils.getContentResolver().delete(UserProvider.USER_CONTENT_URI, whereClause,username);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateUser(User user) {
        if (user == null || TextUtils.isEmpty(user.username)) {
            return;
        }
        ContentValues values = new ContentValues();
        values.put(UserTable.ATTRIBUTE_USER_NAME, user.username);
        values.put(UserTable.ATTRIBUTE_USER_PASSWORD, user.password);
        String whereClause = UserTable.ATTRIBUTE_USER_NAME + " =? ";
        synchronized (mLock) {
            try {
                ResourceUtils.getContentResolver().update(UserProvider.USER_CONTENT_URI, values, whereClause, new String[] {user.username});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Result queryUserByName(String username) {
        String selection = UserTable.ATTRIBUTE_USER_NAME + "= '" + username + "'";
        Cursor cursor;
        synchronized (mLock) {
            cursor = ResourceUtils.getContentResolver().query(UserProvider.USER_CONTENT_URI, null, selection, null, null);
        }
        Result result = new Result();
        if (cursor != null && cursor.moveToFirst()) {
            User user = new User();
            user.id = cursor.getInt(cursor.getColumnIndex(UserTable.ATTRIBUTE_USER_ID));
            user.username = cursor.getString(cursor.getColumnIndex(UserTable.ATTRIBUTE_USER_NAME));
            user.password = cursor.getString(cursor.getColumnIndex(UserTable.ATTRIBUTE_USER_PASSWORD));
            result.setSuccess(user);
            Log.i(TAG, "queryUserByName: success user is " + user.toString());
            cursor.close();

        } else {
            result.code = Result.CODE_ERROR_NOT_EXIST;
        }
        return result;
    }


    public static void deleteAllUsers() {
        synchronized (mLock) {
            try {
                ResourceUtils.getContentResolver().delete(UserProvider.USER_CONTENT_URI, null,null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isExistUser(String username) {
        if (queryUserByName(username) == null) {
            return false;
        }
        return true;
    }

}
