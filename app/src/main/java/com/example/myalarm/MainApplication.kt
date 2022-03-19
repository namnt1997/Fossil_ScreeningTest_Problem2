package com.example.myalarm

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import com.example.myalarm.utilities.ListAlarmManager

class MainApplication : Application(), LifecycleObserver {
    override fun onCreate() {
        super.onCreate()
        ListAlarmManager.refreshAlarm(applicationContext)
    }
}