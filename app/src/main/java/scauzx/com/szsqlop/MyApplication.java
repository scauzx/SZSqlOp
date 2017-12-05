package scauzx.com.szsqlop;

import android.app.Application;
import android.content.Context;

import com.scauzx.utils.AppUtils;

/**
 *
 * @author scauzx
 * @date 2017/12/5
 */

public class MyApplication  extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.init(this);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        AppUtils.setsBaseContext(base);
    }
}
