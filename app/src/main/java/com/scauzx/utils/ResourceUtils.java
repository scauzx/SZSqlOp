package com.scauzx.utils;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 *
 * @author scauzx
 * @date 2017/12/6
 */

public class ResourceUtils {

    public static final Resources  getResources() {
        return AppUtils.getContext().getResources();
    }

    public static final ContentResolver getContentResolver() {
        return AppUtils.getContext().getContentResolver();
    }

    public static Drawable getDrawable(int id) {
        return getResources().getDrawable(id);
    }

}
