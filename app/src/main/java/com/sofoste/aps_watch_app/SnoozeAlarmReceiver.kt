package com.sofoste.aps_watch_app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.Calendar
import com.sofoste.aps_watch_app.AlarmUtils.Companion.setAlarm

class SnoozeAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Stop the current alarm sound
        AlarmManager.ringtone?.stop()

        // Calculate the snooze time
        val snoozeDuration = 5 * 60 * 1000 // 5 minutes in milliseconds
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.MILLISECOND, snoozeDuration)

        // Set a new alarm with the snooze time
        setAlarm(context, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))
    }
}