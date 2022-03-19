package com.example.myalarm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myalarm.data.repository.IAlarmRepository
import com.example.myalarm.model.AlarmData
import com.example.myalarm.utilities.TIME_FORMAT
import java.text.SimpleDateFormat
import java.util.*

class AddAlarmViewModel(private val repository: IAlarmRepository) : ViewModel() {

    var isSelectedTime = MutableLiveData(false)
    var timeString = MutableLiveData("")
    var isRepeat = MutableLiveData(false)

    fun setTime(time: String) {
        timeString.postValue(time)
        isSelectedTime.postValue(true)
    }

    fun getCurrentTime(): String? {
        return timeString.value
    }

    private fun getCurrentAlarm(): AlarmData {
        val sdf = SimpleDateFormat(TIME_FORMAT, Locale.US)
        val cal = Calendar.getInstance()
        cal.time = sdf.parse(timeString.value)
        return AlarmData(cal[Calendar.HOUR_OF_DAY], cal[Calendar.MINUTE], isRepeat.value!!, true)
    }

    suspend fun saveAlarmData() {
        val alarmData = getCurrentAlarm()
        val listAlarm = repository.getListAlarms()
        val exitedAlarm = listAlarm.find { it.hour == alarmData.hour && it.minute == alarmData.minute && it.isRepeat == alarmData.isRepeat }
        if(exitedAlarm != null){
            repository.updateAlarm(exitedAlarm.id, true)
        }else{
            repository.insertAlarm(alarmData)
        }
    }

}