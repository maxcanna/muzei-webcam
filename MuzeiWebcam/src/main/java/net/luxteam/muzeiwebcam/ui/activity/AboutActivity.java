package net.luxteam.muzeiwebcam.ui.activity;
//
//  AboutActivity
//  Muzei Webcam
//
//  Created by massimilianocannarozzo on 01/03/14.
//  Copyright (c) 2014 Muzei Webcam. All rights reserved.
//

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

import net.luxteam.muzeiwebcam.BuildConfig;
import net.luxteam.muzeiwebcam.R;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView mVersionTextView = findViewById(R.id.about_version);
        TextView mCreditsTextView = findViewById(R.id.about_credits);
        ImageView mGplayImageView = findViewById(R.id.about_gplay);
        ImageView mGithubImageView = findViewById(R.id.about_github);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        mVersionTextView.setText(BuildConfig.VERSION_NAME);
        mCreditsTextView.setText(Html.fromHtml(getString(R.string.about_credits)));

        mGplayImageView.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_contacts_gplay)));
            startActivity(browserIntent);
            trackContactEvent("play");
        });

        mGithubImageView.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_contacts_github)));
            startActivity(browserIntent);
            trackContactEvent("gh");
        });
    }

    private void trackContactEvent(String contactName) {
        final Bundle bundle = new Bundle();
        bundle.putString("contact",  contactName);
        mFirebaseAnalytics.logEvent("open_contact", bundle);
    }
}
