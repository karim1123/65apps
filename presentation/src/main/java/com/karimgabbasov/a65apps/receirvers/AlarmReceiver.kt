package com.karimgabbasov.a65apps.receirvers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.content.ContextCompat
import com.karimgabbasov.a65apps.R
import com.karimgabbasov.a65apps.utils.sendBirthdayNotification

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        createChannel(
            context,
            context.getString(R.string.birthday_notification_channel_id),
            context.getString(R.string.birthday_notification_channel_name)
        )
        val name = intent.getStringExtra(NAME).toString()
        val id = intent.getStringExtra(ID).toString()//id фрагмента
        val birthday = intent.getStringExtra(DATE).toString()//дата дня рождения
        val notificationBody = context.getString(R.string.today_birthday, name)

        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.sendBirthdayNotification(
            context,
            id,
            notificationBody,
            intent,
            birthday
        )
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

    companion object {
        private const val NAME = "name"
        private const val ID = "id"
        private const val DATE = "date"
    }
}