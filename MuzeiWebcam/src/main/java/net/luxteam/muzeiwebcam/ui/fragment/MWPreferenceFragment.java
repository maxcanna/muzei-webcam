package net.luxteam.muzeiwebcam.ui.fragment;
//
//  MWPreferenceFragment
//  Muzei Webcam
//
//  Created by massimilianocannarozzo on 25/02/14.
//  Copyright (c) 2014 Muzei Webcam. All rights reserved.
//

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;

import net.luxteam.muzeiwebcam.BuildConfig;
import net.luxteam.muzeiwebcam.R;
import net.luxteam.muzeiwebcam.api.WebcamArtSource;
import net.luxteam.muzeiwebcam.ui.activity.AboutActivity;
import net.luxteam.muzeiwebcam.utils.Utils;

public class MWPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String EXTRA_URL = "extraUrl";

    public MWPreferenceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onResume() {
        super.onResume();

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

        updateSubtitles();

        if(getArguments() != null){
            final Activity a = getActivity();
            final String url = getArguments().getString(EXTRA_URL);
            if(!TextUtils.isEmpty(url) && Patterns.WEB_URL.matcher(url).matches()){
                Utils.storeValue(a, getString(R.string.preference_key_url), url);
                EasyTracker.getTracker().sendEvent("settings", EXTRA_URL, "valid", 0l);
            } else {
                Utils.showToast(a , R.string.error_invalid_url);
                EasyTracker.getTracker().sendEvent("settings", EXTRA_URL, "invalid" ,0l);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        refresh(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_preference_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView mAboutTextView = (TextView) view.findViewById(R.id.about);
        mAboutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AboutActivity.class));
            }
        });
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

        final Activity a = getActivity();

        if(s.equals(a.getString(R.string.preference_key_grab_url))){
            boolean active = Utils.getBooleanValue(a, s);

            EasyTracker.getTracker().sendEvent("settings", s, String.valueOf(active), 0l);

            PackageManager pm = a.getPackageManager();

            pm.setComponentEnabledSetting(
                    new ComponentName(a, BuildConfig.APPLICATION_ID+ ".ShareSettingsActivity"),
                    active ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP
            );
        } else if(s.equals(a.getString(R.string.preference_key_interval))){
            String interval = Utils.getStringValue(a, s);
            if(TextUtils.isEmpty(interval)){
                Utils.storeValue(a, s, "1");
            } else {
                EasyTracker.getTracker().sendEvent("settings", s, String.valueOf(interval), 0l);
            }
        } else if(s.equals(a.getString(R.string.preference_key_url))){
            String url = Utils.getStringValue(a, s);
            if(TextUtils.isEmpty(url) || !Patterns.WEB_URL.matcher(url).matches()){
                if(!TextUtils.isEmpty(url)){
                    Utils.storeValue(a, s, null);
                } else {
                    Utils.showToast(a, R.string.error_invalid_url);
                }
            } else {
                refresh(a);
            }
        }
          
        updateSubtitles();
    }

    private void refresh(Context c) {
        Intent i = new Intent(c,WebcamArtSource.class);
        i.setAction(WebcamArtSource.ACTION_REFRESH);

        c.startService(i);
    }

    private void updateSubtitles() {
        final Activity a = getActivity();

        final PreferenceScreen preferenceScreen = getPreferenceScreen();

        for(int i = 0; i < preferenceScreen.getPreferenceCount(); i++){
            Preference p = preferenceScreen.getPreference(i);
            if(p instanceof EditTextPreference){
                p.setSummary(Utils.getStringValue(a, p.getKey()));
            }
        }
    }
}
