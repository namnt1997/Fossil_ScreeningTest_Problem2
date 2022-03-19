package com.example.myalarm.data.repository

import android.content.Context
import com.example.myalarm.data.AppDatabase
import com.example.myalarm.model.AlarmData

class AlarmRepository(context: Context) : IAlarmRepository {
    private val dao = AppDatabase.getInstance(context).alarmDataDao()

    override fun insertAlarm(alarmData: AlarmData) {
        dao.insertAlarm(alarmData)
    }

    override fun getListAlarms(): List<AlarmData> {
        return dao.getListAlarm()
    }

    override fun updateAlarm(alarmId: Int, isActive: Boolean) {
        dao.updateAlarm(alarmId, isActive)
    }
}