package com.example.myalarm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myalarm.adapter.ListAlarmsAdapter
import com.example.myalarm.data.repository.IAlarmRepository
import com.example.myalarm.model.AlarmData

class ListAlarmViewModel(private val repository: IAlarmRepository) : ViewModel() {

    val adapter = MutableLiveData<ListAlarmsAdapter>()

    fun setAdapter(alarmAdapter: ListAlarmsAdapter) {
        adapter.postValue(alarmAdapter)
    }

    fun getListAlarms(): ArrayList<AlarmData> {
        val listAlarm = ArrayList<AlarmData>()
        listAlarm.addAll(repository.getListAlarms())
        return listAlarm
    }

    suspend fun updateAlarm(alarmData: AlarmData) {
        repository.updateAlarm(alarmData.id, alarmData.isActive)
    }
}