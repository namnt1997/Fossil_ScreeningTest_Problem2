package com.example.myalarm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.myalarm.databinding.FragmentAddAlarmBinding
import com.example.myalarm.utilities.ListAlarmManager
import com.example.myalarm.utilities.TIME_FORMAT
import com.example.myalarm.viewmodel.AddAlarmViewModel
import com.example.myalarm.viewmodel.AddAlarmViewModelFactory
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class AddAlarmFragment : Fragment() {

    companion object {
        fun newInstance() = AddAlarmFragment()
    }

    private val addAlarmViewModel: AddAlarmViewModel by viewModels {
        AddAlarmViewModelFactory(
            requireActivity().applicationContext
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAddAlarmBinding.inflate(layoutInflater, container, false)
        binding.apply {
            viewModel = addAlarmViewModel
            btnSelectTime.setOnClickListener {
                selectTime()
            }
            btnDone.setOnClickListener {
                addAlarm()
            }
            lifecycleOwner = viewLifecycleOwner

            toolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }
        }
        return binding.root
    }

    private fun addAlarm() {
        CoroutineScope(Dispatchers.IO).launch {
            addAlarmViewModel.saveAlarmData()
            withContext(Dispatchers.Main) {
                ListAlarmManager.refreshAlarm(requireActivity().applicationContext)
                findNavController().navigateUp()
            }
        }
    }

    private fun selectTime() {
        val sdf = SimpleDateFormat(TIME_FORMAT, Locale.US)
        val currentTime = addAlarmViewModel.getCurrentTime()

        val picker = if (currentTime?.isNotEmpty() == true) {
            val cal = Calendar.getInstance()
            cal.time = sdf.parse(currentTime)
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(cal[Calendar.HOUR_OF_DAY])
                .setMinute(cal[Calendar.MINUTE])
                .build()
        } else {
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .build()
        }
        picker.addOnPositiveButtonClickListener() {
            val cal = Calendar.getInstance()
            cal[Calendar.HOUR_OF_DAY] = picker.hour
            cal[Calendar.MINUTE] = picker.minute

            addAlarmViewModel.setTime(sdf.format(cal.time))
        }

        picker.show(childFragmentManager, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}