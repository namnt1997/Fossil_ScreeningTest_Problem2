package com.example.myalarm.data.repository

import com.example.myalarm.model.AlarmData

interface IAlarmRepository {

    fun insertAlarm(alarmData: AlarmData)

    fun getListAlarms(): List<AlarmData>

    fun updateAlarm(alarmId: Int, isActive: Boolean)
}