package net.luxteam.muzeiwebcam.ui.activity;
//
//  SettingsActivity
//  Muzei Webcam
//
//  Created by massimilianocannarozzo on 25/02/14.
//  Copyright (c) 2014 Muzei Webcam. All rights reserved.
//

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.analytics.tracking.android.EasyTracker;

import net.luxteam.muzeiwebcam.ui.fragment.MWPreferenceFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MWPreferenceFragment f = new MWPreferenceFragment();

        String s = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        if(s != null){
            Bundle b = new Bundle();
            b.putString(MWPreferenceFragment.EXTRA_URL, s);
            f.setArguments(b);
        }

        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, f)
                .commitAllowingStateLoss();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EasyTracker.getInstance().activityStop(this);
    }

    @Override
    public void onStart(){
        super.onStart();
        EasyTracker.getTracker().sendView("/settings");
    }
}
