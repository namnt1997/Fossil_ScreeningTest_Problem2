package com.example.myalarm.data

import android.content.Context
import com.example.myalarm.data.repository.AlarmRepository
import com.example.myalarm.data.repository.IAlarmRepository

class DatabaseService {
    private lateinit var alarmRepository: IAlarmRepository

    fun initialize(context: Context) {
        alarmRepository = AlarmRepository(context)
    }

    fun getAlarmRepository(): IAlarmRepository {
        return alarmRepository
    }

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: DatabaseService? = null

        fun getInstance(context: Context): DatabaseService {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(
                        context
                    )
                        .also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): DatabaseService {
            val repo = DatabaseService()
            repo.initialize(context)
            return repo
        }
    }
}