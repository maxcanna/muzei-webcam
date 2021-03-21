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
import android.widget.Toast;

import androidx.preference.PreferenceManager;

public class Utils {

    private static Toast t;

    public static void showToast(final Context ctx, int resourceId){
        if(ctx == null) return;

        final String message = ctx.getString(resourceId);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                if (t == null) {
                    t = Toast.makeText(ctx, message, Toast.LENGTH_SHORT);
                } else t.setText(message);

                t.show();
            }
        });
    }

    public static void storeValue(Context ctx, String name, String value){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        prefs.edit().putString(name, value).apply();
    }

    public static boolean getBooleanValue(Context ctx, String name){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        return prefs.getBoolean(name,false);
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
