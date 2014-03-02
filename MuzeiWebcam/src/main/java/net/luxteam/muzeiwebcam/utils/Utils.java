package net.luxteam.muzeiwebcam.utils;
//
//  Utils
//  Muzei Webcam
//
//  Created by massimilianocannarozzo on 25/02/14.
//  Copyright (c) 2014 Muzei Webcam. All rights reserved.
//

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class Utils {

    private static Toast t;

    public static void showToast(final Context ctx, final String message, final int duration){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                if(t == null){
                    t = Toast.makeText(ctx, message, duration);
                } else t.setText(message);

                t.show();
            }
        });
    }

    public static void showToast(Context ctx, int message){
        showToast(ctx,message,Toast.LENGTH_SHORT);
    }

    public static void showToast(Context ctx, int message, int duration){
        if(ctx == null) return;

        showToast(ctx,ctx.getResources().getString(message),duration);
    }

    public static void storeValue(Context ctx, String name, boolean value){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        prefs.edit().putBoolean(name,value).apply();
    }

    public static void storeValue(Context ctx, String name, int value){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        prefs.edit().putInt(name, value).apply();
    }

    public static void storeValue(Context ctx, String name, String value){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        prefs.edit().putString(name, value).apply();
    }

    public static boolean getBooleanValue(Context ctx, String name){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        return prefs.getBoolean(name,false);
    }

    public static int getIntValue(Context ctx, String name){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        return prefs.getInt(name, 0);
    }

    public static String getStringValue(Context ctx, String name){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        return prefs.getString(name, null);
    }

    public static boolean isWifiConnected(Context ctx) {
        ConnectivityManager connManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return  mWifi.isConnected();
    }
}
