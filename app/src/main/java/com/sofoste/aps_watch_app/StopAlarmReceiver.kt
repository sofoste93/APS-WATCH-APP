package com.sofoste.aps_watch_app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class StopAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Stop the alarm sound
        AlarmManager.ringtone?.stop()
    }
}

