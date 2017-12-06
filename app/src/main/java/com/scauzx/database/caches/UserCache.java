package com.scauzx.database.caches;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.scauzx.database.content.UserProvider;
import com.scauzx.database.utils.UserDbUtils;
import com.scauzx.models.Result;
import com.scauzx.models.User;
import com.scauzx.threads.Demon;
import com.scauzx.utils.ListUtils;
import com.scauzx.utils.ResourceUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author scauzx
 * @date 2017/12/6
 */

public class UserCache {

    private ContentObserver observer;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private HashSet<UserInfoChangeListener> mListener;
    private List<User> mUsers = new ArrayList<>();
    public static UserCache sInstance;

    public void register(UserInfoChangeListener userInfoListener) {
        if (mListener == null) {
            mListener = new HashSet<>();
        }
        if (!mListener.contains(userInfoListener)) {
            mListener.add(userInfoListener);
        }
    }


    public void unRegister(UserInfoChangeListener userInfoListener) {
        if (mListener == null) {
            return;
        }
        if (mListener.contains(userInfoListener)) {
            mListener.remove(userInfoListener);
        }
    }

    public List<User> getUserInfo() {
        return mUsers;
    }

    private void notifyListener() {
        if (!ListUtils.isEmpty(mListener)) {
           for (UserInfoChangeListener listener : mListener) {
               if (listener != null) {
                   listener.onUserInfoChange();
               }
           }
        }
    }

    public interface UserInfoChangeListener{
        void onUserInfoChange();
    }

    public interface GetUserInfoListener{
        void onUserInfoSuccess(User user);
        void onUserInfoFailed(int errorCode);
    }


    public static UserCache getInstance() {
        if (sInstance == null) {
            synchronized (UserCache.class) {
                if (sInstance == null) {
                    sInstance = new UserCache();
                }
            }
        }
        return sInstance;
    }

    private Runnable mReloadRunnable = new Runnable() {
        @Override
        public void run() {
            loadFromDB();
        }
    };

    private void loadFromDB() {
        synchronized (mUsers) {
            mUsers = UserDbUtils.getUserInfo();
        }
        Demon.uiHandler().post(new Runnable() {
            @Override
            public void run() {
                notifyListener();
            }
        });
    }

    public void deleteAllUsers() {
        Demon.dbHandler().post(new Runnable() {
            @Override
            public void run() {
                UserDbUtils.deleteAllUsers();
            }
        });
    }


    public void addUser(final User user) {
        if (user != null) {
            Demon.dbHandler().post(new Runnable() {
                @Override
                public void run() {
                    UserDbUtils.addUser(user);
                }
            });
        }
    }

    public void deleteUser(final String username) {
        if (!TextUtils.isEmpty(username)) {
            Demon.dbHandler().post(new Runnable() {
                @Override
                public void run() {
                    UserDbUtils.deleteUser(username);
                }
            });
        }
    }

    public void updateUser(final User user) {
        if (user != null) {
            Demon.dbHandler().post(new Runnable() {
                @Override
                public void run() {
                    UserDbUtils.updateUser(user);
                }
            });
        }
    }

    class GetUserInfoListenerWrap{
        GetUserInfoListener listener;
        public GetUserInfoListenerWrap(GetUserInfoListener listener) {
            this.listener = listener;
        }

        public void onUserInfoSuccess(final User user) {
            Demon.sUIHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (listener != null) {
                        listener.onUserInfoSuccess(user);
                        listener = null;
                    }
                }
            });
        }

        public void onUserInfoFailed(final int code) {
            Demon.uiHandler().post(new Runnable() {
                @Override
                public void run() {
                    if (listener != null) {
                        listener.onUserInfoFailed(code);
                        listener = null;
                    }
                }
            });
        }
    }


    public void queryUserByName(final String username, final GetUserInfoListener listener) {
        final GetUserInfoListenerWrap listenerWrap = new GetUserInfoListenerWrap(listener);
        Demon.dbHandler().post(new Runnable() {
            @Override
            public void run() {
                Result result = UserDbUtils.queryUserByName(username);
                if (result.code == Result.CODE_SUCCESS) {
                    listenerWrap.onUserInfoSuccess(result.user);
                } else {
                    listenerWrap.onUserInfoFailed(result.code);
                }
            }
        });
    }


    public void queryAllUsers() {
        Demon.dbHandler().removeCallbacks(mReloadRunnable);
        Demon.dbHandler().postDelayed(mReloadRunnable, 500);
    }

    public UserCache() {
        observer = new ContentObserver(mHandler) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                queryAllUsers();
            }

            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                queryAllUsers();
            }
        };
        ResourceUtils.getContentResolver().registerContentObserver(UserProvider.USER_CONTENT_URI, true, observer);

    }

}
