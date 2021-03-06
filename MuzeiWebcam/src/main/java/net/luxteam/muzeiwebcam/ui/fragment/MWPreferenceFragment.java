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
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;

import com.google.android.apps.muzei.api.provider.Artwork;
import com.google.android.apps.muzei.api.provider.ProviderClient;
import com.google.android.apps.muzei.api.provider.ProviderContract;
import com.google.firebase.analytics.FirebaseAnalytics;

import net.luxteam.muzeiwebcam.BuildConfig;
import net.luxteam.muzeiwebcam.R;
import net.luxteam.muzeiwebcam.ui.activity.AboutActivity;
import net.luxteam.muzeiwebcam.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

public class MWPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String EXTRA_URL = "extraUrl";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
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
                updateSubtitles();
            } else {
                Utils.showToast(a , R.string.error_invalid_url);
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
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView mAboutTextView = view.findViewById(R.id.about);
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
        final FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(a);
        final Bundle bundle = new Bundle();
        bundle.putString("preference_name",  s);

        if(s.equals(a.getString(R.string.preference_key_grab_url))){
            boolean active = Utils.getBooleanValue(a, s);
            bundle.putString("preference_value",  String.valueOf(active));

            PackageManager pm = a.getPackageManager();

            pm.setComponentEnabledSetting(
                    new ComponentName(a, BuildConfig.APPLICATION_ID+ ".ShareSettingsActivity"),
                    active ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP
            );
        } else if(s.equals(a.getString(R.string.preference_key_name))){
            String name = Utils.getStringValue(a, s);
            bundle.putString("preference_value",  name);
        } else if(s.equals(a.getString(R.string.preference_key_url))){
            String url = Utils.getStringValue(a, s);
            bundle.putString("preference_value",  url);

            if(TextUtils.isEmpty(url) || !Patterns.WEB_URL.matcher(url).matches()){
                Utils.showToast(a, R.string.error_invalid_url);
                Utils.storeValue(a, s, null);
            }

            refresh(a);
        }
          
        updateSubtitles();

        mFirebaseAnalytics.logEvent("preference_changed", bundle);
    }

    private void refresh(Context c) {
        final ProviderClient providerClient = ProviderContract.getProviderClient(c, "net.luxteam.muzeiwebcam");
        final Date now = new Date();
        final String subtitle = SimpleDateFormat.getInstance().format(now);
        final String title = Utils.getStringValue(c, c.getString(R.string.preference_key_name), c.getString(R.string.app_name));
        final String url = Utils.getStringValue(c, c.getString(R.string.preference_key_url), c.getString(R.string.source_default_url));
        final Uri uri = Uri.parse(url);
        final FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(c);

        providerClient.setArtwork(new Artwork.Builder()
                .title(title)
                .byline(subtitle)
                .webUri(uri)
                .token(String.valueOf(now.getTime()))
                .persistentUri(uri)
                .build());

        final Bundle bundle = new Bundle();
        bundle.putString("title",  title);
        bundle.putString("url",  url);
        mFirebaseAnalytics.logEvent("set_artwork", bundle);
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
