package com.ass2.final_project_i190727_i190542_i180580;

import android.app.Application;

import com.onesignal.OneSignal;

public class PZHApplication extends Application {
    private static final String ONESIGNAL_APP_ID = "c40079a8-ce49-4aac-a5ca-c9abc4cb3bcd";

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

        // promptForPushNotifications will show the native Android notification permission prompt.
        // We recommend removing the following code and instead using an In-App Message to prompt for notification permission (See step 7)
        OneSignal.promptForPushNotifications();
    }
}
