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
import android.widget.Toast;

import androidx.preference.PreferenceManager;

public class Utils {

    public static void showToast(final Context ctx, int resourceId){
        if(ctx == null) return;
        Toast.makeText(ctx, ctx.getString(resourceId), Toast.LENGTH_SHORT).show();
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
        return getStringValue(ctx, name, null);
    }

    public static String getStringValue(Context ctx, String name, String defValue){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        return prefs.getString(name, defValue);
    }

    public static boolean isWifiConnected(Context ctx) {
        ConnectivityManager connManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return  mWifi.isConnected();
    }
}
