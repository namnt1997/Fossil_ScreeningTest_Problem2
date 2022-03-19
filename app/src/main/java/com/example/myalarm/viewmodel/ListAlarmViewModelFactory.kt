package com.example.myalarm.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myalarm.data.DatabaseService

class ListAlarmViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListAlarmViewModel(DatabaseService.getInstance(context).getAlarmRepository()) as T
    }
}