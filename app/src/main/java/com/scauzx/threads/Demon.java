package com.scauzx.threads;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 *
 * @author Administrator
 * @date 2017/12/6
 */

public class Demon{

    public static Handler sIOHandler;
    public static String NAME_HANDLER_IO = "name_handler_io";
    public static Handler sUIHandler;

    private static HandlerThread sIOHandlerThread;

    public static Handler dbHandler() {
        if (sIOHandlerThread == null) {
            sIOHandlerThread = new HandlerThread(NAME_HANDLER_IO);
            sIOHandlerThread.start();
        }

        if (sIOHandler == null) {
            sIOHandler = new Handler(sIOHandlerThread.getLooper());
        }
        return sIOHandler;
    }

    public static Handler uiHandler() {
        if (sUIHandler == null) {
            sUIHandler = new Handler(Looper.getMainLooper());
        }
        return sUIHandler;
    }
}
