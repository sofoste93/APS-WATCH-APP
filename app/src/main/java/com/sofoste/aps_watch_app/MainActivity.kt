package com.sofoste.aps_watch_app

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mDigitalClock: TextView
    private lateinit var mLocalTimeButton: Button
    private lateinit var mAfricaTimeButton: Button
    private lateinit var mAnalogClock: AnalogClockView
    private lateinit var mThemeButton: Button
    private var isDarkTheme: Boolean = true

    private val mHandler = Handler(Looper.getMainLooper())

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Set the theme based on the current value of isDarkTheme
        val themes = arrayOf(
            R.style.Theme_MyApp1,
            R.style.Theme_MyApp2,
            R.style.Theme_MyApp3
        )
        val randomTheme = themes.random()

        // Set the theme for the current activity
        setTheme(randomTheme)

        setContentView(R.layout.activity_main) // Keep only this line

        val alarmSettingButton: Button = findViewById(R.id.alarmSettingButton)
        alarmSettingButton.setOnClickListener {
            val intent = Intent(this, AlarmSettingActivity::class.java)
            startActivity(intent)
        }

        mDigitalClock = findViewById(R.id.watchTextView)
        mLocalTimeButton = findViewById(R.id.localTimeButton)
        mAfricaTimeButton = findViewById(R.id.africaTimeButton)
        mAnalogClock = findViewById(R.id.analogClockView)
        mThemeButton = findViewById(R.id.themeButton)

        // Set the current time to local time (GMT+1) by default
        setLocalTime()

        // Set up local time button click listener
        mLocalTimeButton.setOnClickListener {
            setLocalTime()
        }

        // Set up Africa time button click listener
        mAfricaTimeButton.setOnClickListener {
            setCentralAfricaTime()
        }

        // Start the clock update handler
        mHandler.post(mClockRunnable)

        // Set up theme button click listener
        mThemeButton.setOnClickListener {
            // Toggle the isDarkTheme flag
            isDarkTheme = !isDarkTheme

            // Recreate the activity to apply the new theme
            recreate()
        }
    }

    private val mClockRunnable = object : Runnable {
        override fun run() {
            // Update the clock display
            setLocalTime()

            // Schedule the next update in one second
            mHandler.postDelayed(this, 1000)
        }
    }

    private fun setLocalTime() {
        // Get current time in GMT+1 (Germany time)
        val tz = TimeZone.getTimeZone("GMT+1")
        val c = Calendar.getInstance(tz)

        // Format the time in "HH:MM:SS AM/PM GMT+1" format
        val timeFormat = SimpleDateFormat("hh:mm:ss a 'GMT+1'", Locale.getDefault())
        val time = timeFormat.format(c.time)

        // Update the digital clock text
        mDigitalClock.text = time

        // Update the analog clock view
        mAnalogClock.updateTime(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND))
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setCentralAfricaTime() {
        // Get current time in GMT+1 (Central Africa time)
        val tz = TimeZone.getTimeZone("GMT+1")
        val c = Calendar.getInstance(tz)

        // Add one hour to account for daylight saving time (DST)
        if (c.get(Calendar.MONTH) >= Calendar.APRIL && c.get(Calendar.MONTH) <= Calendar.OCTOBER) {
            c.add(Calendar.HOUR_OF_DAY, 1)
        }

        // Format the time in "HH:MM:SS AM/PM WAT" format
        val timeFormat = SimpleDateFormat("hh:mm:ss a 'WAT'", Locale.getDefault())
        val time = timeFormat.format(c.time)

        // Update the digital clock text
        mDigitalClock.text = time

        // Update the analog clock view
        mAnalogClock.updateTime(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND))
    }

    override fun onDestroy() {
        super.onDestroy()

        // Stop the clock update handler
        mHandler.removeCallbacks(mClockRunnable)
    }
}