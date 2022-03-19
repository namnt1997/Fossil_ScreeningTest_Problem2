package com.example.myalarm.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myalarm.data.DatabaseService

class AddAlarmViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = DatabaseService.getInstance(context).getAlarmRepository()
        return AddAlarmViewModel(repository) as T
    }
}