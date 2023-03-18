package com.sofoste.aps_watch_app

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.core.app.NotificationCompat

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // Create an Intent for the activity you want to open when the notification is clicked
        val notificationIntent = Intent(context, MainActivity::class.java)

        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, pendingIntentFlags)

        val notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        val channelId = "alarm_notification_channel"
        val channelName = "Alarm Notifications"

        // Create stop and snooze actions
        val stopIntent = Intent(context, StopAlarmReceiver::class.java)
        val stopPendingIntent = PendingIntent.getBroadcast(context, 0, stopIntent, pendingIntentFlags)
        //val stopAction = NotificationCompat.Action.Builder(R.drawable.ic_stop, "Stop", stopPendingIntent).build()

        val snoozeIntent = Intent(context, SnoozeAlarmReceiver::class.java)
        val snoozePendingIntent = PendingIntent.getBroadcast(context, 0, snoozeIntent, pendingIntentFlags)
        //val snoozeAction = NotificationCompat.Action.Builder(R.drawable.ic_snooze, "Snooze", snoozePendingIntent).build()
        val stopAction = NotificationCompat.Action.Builder(android.R.drawable.ic_media_pause, "Stop", stopPendingIntent).build()
        val snoozeAction = NotificationCompat.Action.Builder(android.R.drawable.ic_menu_recent_history, "Snooze", snoozePendingIntent).build()


        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Alarm")
            .setContentText("Your alarm is ringing!")
            .setAutoCancel(true)
            .setSound(notificationSound)
            .setContentIntent(pendingIntent)
            .addAction(stopAction)
            .addAction(snoozeAction)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Play the alarm sound
        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val ringtone = RingtoneManager.getRingtone(context, uri)

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ALARM)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        ringtone.audioAttributes = audioAttributes
        ringtone.play()

        // Set the ringtone in the AlarmManager
        AlarmManager.ringtone = ringtone

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            ringtone.stop()
        }, 30000) // Stops the ringtone after 30 seconds

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }
}
