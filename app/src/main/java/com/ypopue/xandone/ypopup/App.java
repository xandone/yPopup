package com.ypopue.xandone.ypopup;

import android.app.Application;
import android.content.Context;

/**
 * author: xandone
 * created on: 2017/8/17 23:07
 */

public class App extends Application {
    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }
}
