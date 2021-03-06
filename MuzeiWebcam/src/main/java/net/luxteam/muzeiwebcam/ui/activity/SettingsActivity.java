package net.luxteam.muzeiwebcam.ui.activity;
//
//  SettingsActivity
//  Muzei Webcam
//
//  Created by massimilianocannarozzo on 25/02/14.
//  Copyright (c) 2014 Muzei Webcam. All rights reserved.
//

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

import net.luxteam.muzeiwebcam.ui.fragment.MWPreferenceFragment;

import androidx.fragment.app.FragmentActivity;

public class SettingsActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MWPreferenceFragment f = new MWPreferenceFragment();
        final FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        String s = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        if(s != null){
            Bundle b = new Bundle();
            b.putString(MWPreferenceFragment.EXTRA_URL, s);
            f.setArguments(b);

            final Bundle bundle = new Bundle();
            bundle.putString("url",  s);
            mFirebaseAnalytics.logEvent("grab_url", bundle);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, f)
                .commitAllowingStateLoss();
    }
}
