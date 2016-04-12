package ru.litun.unitingtwist;

import android.app.Application;

/**
 * Created by Litun on 12.04.2016.
 */
public class TwistApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ColorUtils.init(this);
    }
}
