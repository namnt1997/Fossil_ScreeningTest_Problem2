package com.example.myalarm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myalarm.adapter.ListAlarmsAdapter
import com.example.myalarm.databinding.FragmentListAlarmBinding
import com.example.myalarm.model.AlarmData
import com.example.myalarm.utilities.ListAlarmManager
import com.example.myalarm.viewmodel.ListAlarmViewModel
import com.example.myalarm.viewmodel.ListAlarmViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListAlarmFragment : Fragment(), ListAlarmsAdapter.OnSwitchAlarmListener {

    companion object {
        fun newInstance() = ListAlarmFragment()
    }

    private val listAlarmViewModel: ListAlarmViewModel by viewModels {
        ListAlarmViewModelFactory(
            requireActivity().applicationContext
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentListAlarmBinding.inflate(layoutInflater, container, false)
        binding.apply {
            viewModel = listAlarmViewModel
            btnAddAlarm.setOnClickListener {
                addAlarm()
            }
            toolbar.setNavigationOnClickListener { view ->
                activity?.finish()
            }
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    private fun addAlarm() {
        findNavController().navigate(R.id.action_listAlarmFragment_to_addAlarmFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            val listAlarms = listAlarmViewModel.getListAlarms()
            withContext(Dispatchers.Main) {
                val adapter =
                    ListAlarmsAdapter(
                        listAlarms,
                        requireActivity().applicationContext,
                        this@ListAlarmFragment
                    )
                listAlarmViewModel.setAdapter(adapter)
            }
        }
    }

    override fun onSwitchAlarm(alarmData: AlarmData) {
        CoroutineScope(Dispatchers.IO).launch {
            listAlarmViewModel.updateAlarm(alarmData)
            withContext(Dispatchers.Main){
                ListAlarmManager.refreshAlarm(requireActivity().applicationContext)
            }
        }

    }
}