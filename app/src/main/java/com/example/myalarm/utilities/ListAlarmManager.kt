package com.example.myalarm.utilities

import android.content.Context
import android.content.Intent
import com.example.myalarm.data.DatabaseService
import com.example.myalarm.model.AlarmData
import com.example.myalarm.service.AlarmService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

object ListAlarmManager {
    fun refreshAlarm(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val listCurrentAlarm = getListCurrentAlarm(context)
            val nestedAlarm = getNestedAlarm(listCurrentAlarm)
            withContext(Dispatchers.Main) {
                if(nestedAlarm != null){
                    addAlarm(nestedAlarm, context)
                    startService(context)
                }else{
                    stopService(context)
                }
            }
        }
    }

    private fun stopService(context: Context) {
        val intent = Intent( context, AlarmService::class.java)
        context.stopService(intent)
    }

    private fun startService(context: Context) {
        val intent = Intent( context, AlarmService::class.java)
        context.startService(intent)
    }

    private fun addAlarm(alarmData: AlarmData, context: Context) {
        AlarmHelper.cancelAlarm(context)

        val alarmCal = Calendar.getInstance()
        alarmCal[Calendar.HOUR_OF_DAY] = alarmData.hour
        alarmCal[Calendar.MINUTE] = alarmData.minute
        alarmCal[Calendar.SECOND] = 0
        alarmCal[Calendar.MILLISECOND] = 0
        if (alarmCal > Calendar.getInstance()) {
            AlarmHelper.createAlarm(context, alarmCal.timeInMillis)
        } else {
            alarmCal.add(Calendar.DATE, 1)
            AlarmHelper.createAlarm(context, alarmCal.timeInMillis)
        }
    }

    private fun getNestedAlarm(listAlarm: List<AlarmData>): AlarmData? {
        val listAfter = ArrayList<AlarmData>()
        val listBefore = ArrayList<AlarmData>()

        val cal = Calendar.getInstance()
        val currentHour = cal.get(Calendar.HOUR_OF_DAY)
        val currentMinute = cal.get(Calendar.MINUTE)

        listBefore.addAll(listAlarm.filter { it.hour < currentHour })
        listAfter.addAll(listAlarm.filter { it.hour > currentHour })
        val listInHour = listAlarm.filter { it.hour == currentHour }
        listBefore.addAll(listInHour.filter { it.minute <= currentMinute })
        listAfter.addAll(listInHour.filter { it.minute > currentMinute })

        val listAfterSorted = listAfter.sortedWith(compareBy({ it.hour }, { it.minute }))
        val listBeforeSorted = listBefore.sortedWith(compareBy({ it.hour }, { it.minute }))

        val afterAlarmActive = listAfterSorted.find { it.isActive }
        afterAlarmActive?.let {
            return afterAlarmActive
        }
        val beforeAlarmActive = listBeforeSorted.find { it.isActive && it.isRepeat }
        beforeAlarmActive?.let {
            return beforeAlarmActive
        }

        return null
    }

    private suspend fun getListCurrentAlarm(context: Context): List<AlarmData> {
        val repository = DatabaseService.getInstance(context).getAlarmRepository()
        return repository.getListAlarms()
    }
}