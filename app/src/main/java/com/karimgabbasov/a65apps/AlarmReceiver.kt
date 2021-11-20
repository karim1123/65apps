package com.karimgabbasov.a65apps

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.content.ContextCompat
import com.karimgabbasov.a65apps.utils.sendBirthdayNotification

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        createChannel(
            context,
            context.getString(R.string.birthday_notification_channel_id),
            context.getString(R.string.birthday_notification_channel_name)
        )
        val reminderBody: String = intent.getStringExtra(NAME).toString()
        val id: String = intent.getStringExtra(ID).toString()//id фрагмента
        val date: String? = intent.getStringExtra(DATE)//дата дня рождения
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.sendBirthdayNotification(context, id, reminderBody, intent, date)
    }

    private fun createChannel(context: Context, channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
                .apply {
                    setShowBadge(false)
                }
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)

            val notificationManager = ContextCompat.getSystemService(
                context,
                NotificationManager::class.java
            ) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    companion object{
        private const val NAME = "name"
        private const val ID = "id"
        private const val DATE = "date"
    }
}