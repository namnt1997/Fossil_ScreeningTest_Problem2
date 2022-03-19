package com.example.myalarm.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.myalarm.utilities.ListAlarmManager
import com.example.myalarm.utilities.NotificationUseCase

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("AlarmReceiver", "onReceive")
        context?.let {
            NotificationUseCase.showAlarmNotification(it)
            ListAlarmManager.refreshAlarm(context.applicationContext)
        }
    }
}