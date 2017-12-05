package com.scauzx.utils;

import android.app.Application;
import android.content.Context;

/**
 *
 * @author scauzx
 * @date 2017/12/5
 */

public class AppUtils {
    public static Context sAppContext;
    public static Context sBaseContext;


    public static void init(Application context) {
        sAppContext = context;
    }


    public static void setsBaseContext(Context context) {
        sBaseContext = context;
    }

    public static final Context getContext() {
        return sAppContext == null ? sBaseContext : sAppContext;
    }


}
