package com.example.myalarm.utilities

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.myalarm.receiver.AlarmReceiver
import java.text.SimpleDateFormat
import java.util.*

object AlarmHelper {

    private const val ALARM = 1000
    const val ACTION_AlARM = "ACTION_AlARM"

    fun createAlarm(context: Context, timeMs: Long) {
        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.US)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeMs
        Log.d("AlarmHelper", "createAlarm at: ${sdf.format(calendar.time)}")

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmReceiver::class.java)
        intent.action = ACTION_AlARM
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                ALARM, intent, PendingIntent.FLAG_UPDATE_CURRENT
            )

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeMs, pendingIntent)
    }

    fun cancelAlarm(context: Context) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                ALARM, intent, PendingIntent.FLAG_NO_CREATE
            )
        if (pendingIntent != null) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
        }
    }
}