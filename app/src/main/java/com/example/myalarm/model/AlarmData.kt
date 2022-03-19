package com.example.myalarm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "AlarmData")
data class AlarmData(
    @ColumnInfo(name = "hour")
    var hour: Int,

    @ColumnInfo(name = "minute")
    val minute: Int,

    @ColumnInfo(name = "isRepeat")
    val isRepeat: Boolean,

    @ColumnInfo(name = "isActive")
    var isActive: Boolean,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0
) : Serializable
