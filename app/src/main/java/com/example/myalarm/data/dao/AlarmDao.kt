package com.example.myalarm.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myalarm.model.AlarmData

@Dao
interface AlarmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlarm(alarm: AlarmData)

    @Query("SELECT * FROM AlarmData")
    fun getListAlarm(): List<AlarmData>

    @Query("UPDATE AlarmData SET isActive = :isActive WHERE id = :alarmId")
    fun updateAlarm(alarmId: Int, isActive: Boolean)
}