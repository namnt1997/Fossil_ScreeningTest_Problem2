package com.example.myalarm.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.myalarm.utilities.ListAlarmManager
import com.example.myalarm.utilities.NotificationUseCase

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            NotificationUseCase.showAlarmNotification(it)
            ListAlarmManager.refreshAlarm(context.applicationContext)
        }
    }
}