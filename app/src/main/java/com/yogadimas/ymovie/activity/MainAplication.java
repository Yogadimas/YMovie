package com.yogadimas.ymovie.activity;

import android.app.Application;

import com.orhanobut.hawk.Hawk;

public class MainAplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Hawk.init(this).build();
    }
}
