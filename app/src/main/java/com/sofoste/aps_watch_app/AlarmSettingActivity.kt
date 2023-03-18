package com.sofoste.aps_watch_app
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.*
import java.util.*
import com.sofoste.aps_watch_app.AlarmUtils.Companion.setAlarm

class AlarmSettingActivity : AppCompatActivity() {

    private lateinit var timePicker: TimePicker
    private lateinit var setAlarmButton: Button
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_setting)

        backButton = findViewById(R.id.backButton)
        timePicker = findViewById(R.id.timePicker)
        setAlarmButton = findViewById(R.id.setAlarmButton)

        backButton.setOnClickListener {
            finish()
        }

        setAlarmButton.setOnClickListener {
            val hour = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePicker.hour
            } else {
                timePicker.currentHour
            }
            val minute = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePicker.minute
            } else {
                timePicker.currentMinute
            }
            setAlarm(this, hour, minute)
            Toast.makeText(this, "Alarm set for $hour:$minute", Toast.LENGTH_SHORT).show()
        }
        /*
        setAlarmButton.setOnClickListener {
            // Get the selected time from the TimePicker
            val hour = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePicker.hour
            } else {
                TODO("VERSION.SDK_INT < M")
            }
            val minute = timePicker.minute

            // Set the alarm based on the selected time
            setAlarm(hour, minute)
        }
        */
    }

    private fun setAlarm(hour: Int, minute: Int) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Create an Intent for the AlarmReceiver
        val alarmIntent = Intent(this, AlarmReceiver::class.java)

        // Create a PendingIntent to be triggered by the alarm
        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        val pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, pendingIntentFlags)

        // Set the alarm time
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        Toast.makeText(this, "Alarm set for $hour:$minute", Toast.LENGTH_SHORT).show()

        // Set the alarm (exact and wakes up the device)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }
    }
}
