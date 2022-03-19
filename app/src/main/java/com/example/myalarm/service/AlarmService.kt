package com.example.myalarm.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Observer
import com.example.myalarm.MainActivity
import com.example.myalarm.R
import com.example.myalarm.utilities.NotificationUseCase
import java.util.*

class AlarmService : Service() {
    override fun onCreate() {
        super.onCreate()

        val notification = NotificationUseCase.buildServiceNotification(applicationContext)
        val id = NotificationUseCase.generateRandomId()
        startForeground(id, notification)
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}