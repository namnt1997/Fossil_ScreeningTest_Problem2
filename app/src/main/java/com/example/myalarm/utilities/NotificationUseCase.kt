package com.example.myalarm.utilities

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.myalarm.MainActivity
import com.example.myalarm.R
import java.text.SimpleDateFormat
import java.util.*


object NotificationUseCase {
    private const val alarmChannelId = "alarm_notification"
    private const val alarmChannelName = "Alarm notification"
    private const val alarmNotificationTitle = "Clock"

    private const val serviceChannelId = "service_notification"
    private const val serviceChannelName = "Service notification"
    private const val serviceNotificationTitle = "Alarm service"
    private const val serviceNotificationContent = "Running alarm service..."

    fun showAlarmNotification(context: Context) {
        val notification = buildAlarmNotification(context)

        val mNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        mNotificationManager!!.notify(generateRandomId(), notification)
    }

    fun generateRandomId(): Int {
        val random = Random()
        return random.nextInt(9999 - 1000) + 1000
    }

    private fun buildAlarmNotification(context: Context): Notification {
        val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createAlarmNotificationChannel(context)
        } else {
            alarmChannelId
        }
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat(TIME_FORMAT, Locale.US)

        val notificationIntent = Intent(context, MainActivity::class.java)
        notificationIntent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        val requestID = System.currentTimeMillis().toInt()
        val pendingIntent = PendingIntent.getActivity(
            context,
            requestID,
            notificationIntent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(alarmNotificationTitle)
            .setContentText(sdf.format(cal.time))
            .setContentIntent(pendingIntent)

        //Vibration
        mBuilder.setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000, 1000, 1000))

        //LED
        mBuilder.setLights(Color.RED, 3000, 3000)

        //Ton
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
        }

        return mBuilder.build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createAlarmNotificationChannel(context: Context): String {
        val chan = NotificationChannel(
            alarmChannelId,
            alarmChannelName, NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()
        chan.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM), audioAttributes)
        val service = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return alarmChannelId
    }


    fun buildServiceNotification(context: Context): Notification {
        val channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createServiceNotificationChannel(context)
        } else {
            serviceChannelId
        }

        val notificationIntent = Intent(context, MainActivity::class.java)
        notificationIntent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        val requestID = System.currentTimeMillis().toInt()
        val pendingIntent = PendingIntent.getActivity(
            context,
            requestID,
            notificationIntent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(serviceNotificationTitle)
            .setContentText(serviceNotificationContent)
            .setContentIntent(pendingIntent)

        return mBuilder.build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createServiceNotificationChannel(context: Context): String {
        val chan = NotificationChannel(
            serviceChannelId,
            serviceChannelName, NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return serviceChannelId
    }


}