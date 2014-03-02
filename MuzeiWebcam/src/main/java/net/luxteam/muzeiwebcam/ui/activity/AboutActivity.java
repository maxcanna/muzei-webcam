package net.luxteam.muzeiwebcam.ui.activity;
//
//  AboutActivity
//  Muzei Webcam
//
//  Created by massimilianocannarozzo on 01/03/14.
//  Copyright (c) 2014 Muzei Webcam. All rights reserved.
//

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;

import net.luxteam.muzeiwebcam.BuildConfig;
import net.luxteam.muzeiwebcam.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AboutActivity extends Activity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        CalligraphyConfig.initDefault("fonts/Roboto-Light.ttf");

        TextView mVersionTextView = (TextView) findViewById(R.id.about_version);
        TextView mCreditsTextView = (TextView) findViewById(R.id.about_credits);
        ImageView mTwitterImageView = (ImageView) findViewById(R.id.about_twitter);
        ImageView mFacebookImageView = (ImageView) findViewById(R.id.about_facebook);
        ImageView mGplusImageView = (ImageView) findViewById(R.id.about_gplus);
        ImageView mGplayImageView = (ImageView) findViewById(R.id.about_gplay);
        ImageView mGithubImageView = (ImageView) findViewById(R.id.about_github);

        mVersionTextView.setText(BuildConfig.VERSION_NAME);
        mCreditsTextView.setText(Html.fromHtml(getString(R.string.about_credits)));

        mTwitterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyTracker.getTracker().sendEvent("about","contact","twitter", 0l);

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_contacts_twitter)));
                startActivity(browserIntent);
            }
        });

        mFacebookImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyTracker.getTracker().sendEvent("about","contact","facebook", 0l);

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_contacts_facebook)));
                startActivity(browserIntent);
            }
        });

        mGplusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyTracker.getTracker().sendEvent("about","contact","gplus", 0l);

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_contacts_gplus)));
                startActivity(browserIntent);
            }
        });

        mGplayImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyTracker.getTracker().sendEvent("about","contact","gplay", 0l);

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_contacts_gplay)));
                startActivity(browserIntent);
            }
        });

        mGithubImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyTracker.getTracker().sendEvent("about","contact","github", 0l);

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_contacts_github)));
                startActivity(browserIntent);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        EasyTracker.getInstance().activityStop(this);
    }

    @Override
    public void onStart(){
        super.onStart();
        EasyTracker.getTracker().sendView("/about");
    }
}
