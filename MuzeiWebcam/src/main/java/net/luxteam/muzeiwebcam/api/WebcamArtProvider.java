package net.luxteam.muzeiwebcam.api;
//
//  WebcamArtProvider
//  Muzei Webcam
//
//  Created by massimilianocannarozzo on 07/03/21.
//  Copyright (c) 2021 Muzei Webcam. All rights reserved.
//

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.google.android.apps.muzei.api.provider.Artwork;
import com.google.android.apps.muzei.api.provider.MuzeiArtProvider;

import net.luxteam.muzeiwebcam.R;
import net.luxteam.muzeiwebcam.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WebcamArtProvider extends MuzeiArtProvider {

    @Override
    public void onLoadRequested(boolean firstTime) {
        final Date now = new Date();
        final Artwork lastAddedArtwork = getLastAddedArtwork();

        if (lastAddedArtwork != null && now.getTime() - lastAddedArtwork.getDateAdded().getTime() < 10000) {
            return;
        }

        Context ctx = this.getContext();
        String subtitle = SimpleDateFormat.getInstance().format(now);
        String title = Utils.getStringValue(ctx, ctx.getString(R.string.preference_key_name), ctx.getString(R.string.app_name));
        String url = Utils.getStringValue(ctx, ctx.getString(R.string.preference_key_url));
        String viewUrl = url;

        if(TextUtils.isEmpty(url)){
            url = ctx.getString(R.string.source_default_url);
            viewUrl = ctx.getString(R.string.source_default_view_url);
            title = ctx.getString(R.string.app_name);
            subtitle = ctx.getString(R.string.source_default_subtitle);
        }

        final Artwork artwork = new Artwork.Builder()
                .title(title)
                .byline(subtitle)
                .webUri(Uri.parse(viewUrl))
                .persistentUri(Uri.parse(url))
                .build();
        setArtwork(artwork);
    }

    @NotNull
    @Override
    public String getDescription() {
        final Context ctx = getContext();
        return  TextUtils.isEmpty(Utils.getStringValue(ctx, ctx.getString(R.string.preference_key_url)))
                ? ctx.getString(R.string.source_default_subtitle)
                : Utils.getStringValue(
                    ctx,
                    ctx.getString(R.string.preference_key_name),
                    ctx.getString(R.string.source_description)
                    );
    }
}
