package com.scauzx.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import com.scauzx.models.User;
import com.scauzx.utils.TableUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author scauzx
 * @date 2017/12/5
 */

public class UserDaoImp implements UserDao {
    public static UserDaoImp sInstance;
    private SQLiteDatabase mDataBase;
    private final String TAG = UserDaoImp.class.getSimpleName();

    public static UserDaoImp getInstance() {
        if (sInstance == null) {
            synchronized (UserDaoImp.class) {
                if (sInstance == null) {
                    sInstance = new UserDaoImp();
                }
            }
        }
        return sInstance;
    }

    public void initDataBase() {
        if (mDataBase == null || !mDataBase.isOpen()) {
            mDataBase = UserDataBaseFactory.getDataBase();
        }
    }



    @Override
    public long addUser(User user) {
        long id = 0;
        if (user == null || isExist(user)) {
            return id;
        }
        initDataBase();
        mDataBase.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(TableUtils.ATTRIBUTE_USER_NAME, user.username);
            values.put(TableUtils.ATTRIBUTE_USER_PASSWORD, user.password);
            id = mDataBase.insert(TableUtils.TABLE_USER_NAME, null, values);
            mDataBase.setTransactionSuccessful();
            Log.i(TAG, "addUser: success");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDataBase.endTransaction();
            mDataBase.close();
        }
        return id;
    }



    @Override
    public boolean deleteUser(String username) {
        if (TextUtils.isEmpty(username) || !isExist(username)) {
            return false;
        }
        initDataBase();
        mDataBase.beginTransaction();
        try {
            String whereClause = TableUtils.ATTRIBUTE_USER_NAME + " =? ";
            String[] whereArgs = new String[] {username};
            mDataBase.delete(TableUtils.TABLE_USER_NAME, whereClause, whereArgs);
            mDataBase.setTransactionSuccessful();
            Log.i(TAG, "deleteUser: success");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDataBase.endTransaction();
            mDataBase.close();
        }
        return true;
    }



    @Override
    public boolean updateUser(User user) {
        if (isExist(user)) {
            initDataBase();
            mDataBase.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                values.put(TableUtils.ATTRIBUTE_USER_NAME, user.username);
                values.put(TableUtils.ATTRIBUTE_USER_PASSWORD, user.password);
                String whereClause = TableUtils.ATTRIBUTE_USER_NAME + " =? ";
                mDataBase.update(TableUtils.TABLE_USER_NAME, values, whereClause, new String[] {user.username});
                mDataBase.setTransactionSuccessful();
                Log.i(TAG, "updateUser: success");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mDataBase.endTransaction();
                mDataBase.close();
            }
            return true;
        }
        return false;
    }

    @Override
    public User queryUserByName(String username) {
        User user = null;
        if (!TextUtils.isEmpty(username)) {
            initDataBase();
            mDataBase.beginTransaction();
            try {
                String selection = TableUtils.ATTRIBUTE_USER_NAME + "= '" + username + "'";
                Cursor cursor = mDataBase.query(TableUtils.TABLE_USER_NAME, null, selection, null, null, null, null);
                if (cursor.moveToFirst()) {
                    user = new User();
                    user.id = cursor.getInt(cursor.getColumnIndex(TableUtils.ATTRIBUTE_USER_ID));
                    user.username = cursor.getString(cursor.getColumnIndex(TableUtils.ATTRIBUTE_USER_NAME));
                    user.password = cursor.getString(cursor.getColumnIndex(TableUtils.ATTRIBUTE_USER_PASSWORD));
                    Log.i(TAG, "queryUserByName: success user is " + user.toString());
                }
                mDataBase.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mDataBase.endTransaction();
                mDataBase.close();
            }
        }
        return user;
    }



    @Override
    public List<User> getUsers() {
        initDataBase();
        List<User> users = new ArrayList<>();
        mDataBase.beginTransaction();
        try {
            Cursor cursor = mDataBase.query(TableUtils.TABLE_USER_NAME, null,null,null,null,null,null,null);
            while (cursor.moveToNext()) {
                User user = new User();
                user.id = cursor.getInt(cursor.getColumnIndex(TableUtils.ATTRIBUTE_USER_ID));
                user.username = cursor.getString(cursor.getColumnIndex(TableUtils.ATTRIBUTE_USER_NAME));
                user.password = cursor.getString(cursor.getColumnIndex(TableUtils.ATTRIBUTE_USER_PASSWORD));
                users.add(user);
            }
            if (cursor != null) {
                cursor.close();
            }
            mDataBase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mDataBase.endTransaction();
            mDataBase.close();
        }
        Log.i(TAG, "getUsers: users = " + users.toString());
        return users;
    }

    @Override
    public boolean isExist(User user) {
        if (user != null) {
            if (queryUserByName(user.username) != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isExist(String username) {
        if (TextUtils.isEmpty(username)) {
            return false;
        }
        if (queryUserByName(username) != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAllUser() {
        initDataBase();
        mDataBase.beginTransaction();
        try {
            mDataBase.delete(TableUtils.TABLE_USER_NAME, null, null);
            mDataBase.setTransactionSuccessful();
            Log.i(TAG, "deleteAllUser: success");
        } catch (Exception e) {
         e.printStackTrace();
        } finally {
            mDataBase.endTransaction();
            mDataBase.close();
        }
        return false;
    }
}
