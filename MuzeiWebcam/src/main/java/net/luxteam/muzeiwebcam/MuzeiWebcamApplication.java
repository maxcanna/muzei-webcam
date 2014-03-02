package net.luxteam.muzeiwebcam;
//
//  MuzeiWebcamApplication
//  Muzei Webcam
//
//  Created by massimilianocannarozzo on 01/03/14.
//  Copyright (c) 2014 Muzei Webcam. All rights reserved.
//

import android.app.Application;
import com.google.analytics.tracking.android.EasyTracker;

public class MuzeiWebcamApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        EasyTracker.getInstance().setContext(this);
    }
}
