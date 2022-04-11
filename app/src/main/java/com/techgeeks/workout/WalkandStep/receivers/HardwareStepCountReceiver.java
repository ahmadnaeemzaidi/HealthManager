
package com.techgeeks.workout.WalkandStep.receivers;

import android.content.Context;
import android.content.Intent;

import androidx.legacy.content.WakefulBroadcastReceiver;

import android.util.Log;

import com.techgeeks.workout.WalkandStep.services.HardwareStepCounterService;



public class HardwareStepCountReceiver extends WakefulBroadcastReceiver {
    private static final String LOG_CLASS = HardwareStepCountReceiver.class.getName();

    @Override
    public void onReceive(final Context context, Intent intent) {
        try{
            Log.i(LOG_CLASS, "Received hardware step count alarm");
            Intent serviceIntent = new Intent(context, HardwareStepCounterService.class);
            context.startService(serviceIntent);
        }catch (Exception e){

        }

    }
}
