package com.decerno.koeapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by jear on 2015-01-20.
 */
public class StartFirebaseAtBoot extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, FirebaseBackgroundService.class);
        context.startService(serviceIntent);
    }
}
