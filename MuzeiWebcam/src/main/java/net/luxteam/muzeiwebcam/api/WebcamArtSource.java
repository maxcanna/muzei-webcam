package net.luxteam.muzeiwebcam.api;
//
//  WebcamArtSource
//  Muzei Webcam
//
//  Created by massimilianocannarozzo on 25/02/14.
//  Copyright (c) 2014 Muzei Webcam. All rights reserved.
//

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.apps.muzei.api.Artwork;
import com.google.android.apps.muzei.api.MuzeiArtSource;
import com.google.android.apps.muzei.api.UserCommand;

import net.luxteam.muzeiwebcam.R;
import net.luxteam.muzeiwebcam.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WebcamArtSource extends MuzeiArtSource {

    private static final int CUSTOM_COMMAND_ID_REFRESH = 1;
    private static final String SOURCE_NAME = "WebcamArtSource";
    public static final String ACTION_REFRESH = "actionRefresh";

    public WebcamArtSource(){
        super(SOURCE_NAME);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onCustomCommand(int id) {
        if(id == CUSTOM_COMMAND_ID_REFRESH){
            scheduleUpdate(System.currentTimeMillis() + 1000);
            return;
        }

        super.onCustomCommand(id);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            super.onHandleIntent(intent);
            return;
        }

        if(intent.getAction().equals(ACTION_REFRESH)){
            scheduleUpdate(System.currentTimeMillis() + 1000);
            return;
        }

        super.onHandleIntent(intent);
    }

    @Override
    protected void onUpdate(int reason) {
        String subtitle = SimpleDateFormat.getInstance().format(new Date());
        String title = Utils.getStringValue(this, getString(R.string.preference_key_name));
        String url = Utils.getStringValue(this, getString(R.string.preference_key_url));
        String viewUrl = url;
        String val = Utils.getStringValue(this, getString(R.string.preference_key_interval));
        int interval;

        if(!TextUtils.isEmpty(val) && TextUtils.isDigitsOnly(val)){
            interval = Integer.valueOf(val);
        } else {
            interval = Integer.valueOf(getString(R.string.source_default_interval));
        }

        interval *= 1000 * 60;

        if (Utils.getBooleanValue(this, getString(R.string.preference_key_wifi_only)) &&
                !Utils.isWifiConnected(this)){
            scheduleUpdate(System.currentTimeMillis() + interval);

            EasyTracker.getTracker().sendEvent("update", "skipped", "wifi", 0l);

            return;
        }

        if(TextUtils.isEmpty(title)){
            title = getString(R.string.app_name);
        }

        if(TextUtils.isEmpty(url)){
            url = getString(R.string.source_default_url);
            viewUrl = getString(R.string.source_default_view_url);
            subtitle = getString(R.string.source_default_subtitle);
        } else {
            setUserCommands(new UserCommand(CUSTOM_COMMAND_ID_REFRESH, getString(R.string.command_refresh)));
        }

        EasyTracker.getTracker().sendEvent("update", "start", null, (long)interval);

        publishArtwork(new Artwork.Builder()
                .title(title)
                .byline(subtitle)
                .viewIntent(new Intent(Intent.ACTION_VIEW, Uri.parse(viewUrl)))
                .token(String.valueOf(System.currentTimeMillis()))
                .imageUri(Uri.parse(url + "?dummy=" + String.valueOf(System.currentTimeMillis())))
                .build());

        scheduleUpdate(System.currentTimeMillis() + interval);
    }

    @Override
    protected void onEnabled() {
        super.onEnabled();
        EasyTracker.getTracker().sendEvent("source", "enabled", null, 0l);
    }

    @Override
    protected void onDisabled() {
        super.onDisabled();
        EasyTracker.getTracker().sendEvent("source", "disabled", null, 0l);
    }
}
