package net.luxteam.muzeiwebcam.ui.activity;
//
//  AboutActivity
//  Muzei Webcam
//
//  Created by massimilianocannarozzo on 01/03/14.
//  Copyright (c) 2014 Muzei Webcam. All rights reserved.
//

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.luxteam.muzeiwebcam.BuildConfig;
import net.luxteam.muzeiwebcam.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView mVersionTextView = (TextView) findViewById(R.id.about_version);
        TextView mCreditsTextView = (TextView) findViewById(R.id.about_credits);
        ImageView mTwitterImageView = (ImageView) findViewById(R.id.about_twitter);
        ImageView mFacebookImageView = (ImageView) findViewById(R.id.about_facebook);
        ImageView mGplayImageView = (ImageView) findViewById(R.id.about_gplay);
        ImageView mGithubImageView = (ImageView) findViewById(R.id.about_github);

        mVersionTextView.setText(BuildConfig.VERSION_NAME);
        mCreditsTextView.setText(Html.fromHtml(getString(R.string.about_credits)));

        mTwitterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_contacts_twitter)));
                startActivity(browserIntent);
            }
        });

        mFacebookImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_contacts_facebook)));
                startActivity(browserIntent);
            }
        });

        mGplayImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_contacts_gplay)));
                startActivity(browserIntent);
            }
        });

        mGithubImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_contacts_github)));
                startActivity(browserIntent);
            }
        });
    }
}
