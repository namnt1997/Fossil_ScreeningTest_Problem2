package com.example.myalarm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myalarm.R
import com.example.myalarm.databinding.LayoutAlarmItemBinding
import com.example.myalarm.model.AlarmData
import com.example.myalarm.utilities.TIME_FORMAT
import java.text.SimpleDateFormat
import java.util.*

class ListAlarmsAdapter(
    private val listData: ArrayList<AlarmData>,
    private val context: Context,
    private val listener: OnSwitchAlarmListener
) :
    RecyclerView.Adapter<ListAlarmsAdapter.ListAlarmsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAlarmsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            LayoutAlarmItemBinding.inflate(layoutInflater, parent, false)
        return ListAlarmsViewHolder(binding, context, listener)
    }

    override fun onBindViewHolder(holder: ListAlarmsViewHolder, position: Int) {
        holder.bing(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class ListAlarmsViewHolder(
        private val binding: LayoutAlarmItemBinding,
        private val context: Context,
        private val listener: OnSwitchAlarmListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bing(alarmData: AlarmData) {
            binding.apply {
                txtTimeAlarm.text = formatTime(alarmData)
                txtRepeatAlarm.text = if (alarmData.isRepeat) {
                    context.getString(R.string.list_alarm_daily)
                } else {
                    context.getString(R.string.list_alarm_once)
                }
                switchAlarm.isChecked = alarmData.isActive

                switchAlarm.setOnCheckedChangeListener { buttonView, isChecked ->
                    alarmData.isActive = isChecked
                    listener.onSwitchAlarm(alarmData)
                }
            }
        }

        private fun formatTime(alarmData: AlarmData): String {
            val cal = Calendar.getInstance()
            cal[Calendar.HOUR_OF_DAY] = alarmData.hour
            cal[Calendar.MINUTE] = alarmData.minute
            val sdf = SimpleDateFormat(TIME_FORMAT, Locale.US)
            return sdf.format(cal.time)
        }
    }

    interface OnSwitchAlarmListener {
        fun onSwitchAlarm(alarmData: AlarmData)
    }
}