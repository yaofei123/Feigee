package com.feigee.feigee.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by fei.yao on 7/29/15.
 */
public class MainApplication extends Application {
    private static Context mContext;

    public static Context getContext()
    {
        return mContext;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mContext = getApplicationContext();
    }
}
