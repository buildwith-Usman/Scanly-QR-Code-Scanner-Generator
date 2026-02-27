package com.infinity.interactive.scanqr.generateqr.activity;

import android.app.Application;


public class MyApp extends Application {

    private static MyApp instance;

    public static MyApp getInstance() {
        return instance;
    }


    @Override
    public void onCreate() {

        super.onCreate();

        instance = this;

//        String[] AD_UNIT_IDS = {getResources().getString(R.string.app_open_id),
//                getResources().getString(R.string.app_open_id_2),
//                getResources().getString(R.string.app_open_id_3)};
//        new AppOpenScript(this, AD_UNIT_IDS);  // to open app open


//        AppOpenManagerNew appOpenManager = new AppOpenManagerNew(this);
//        try {
//            Constants.oldDate = new Date(System.currentTimeMillis());
//        } catch (Exception ignored) {
//
//        }

    }


}
