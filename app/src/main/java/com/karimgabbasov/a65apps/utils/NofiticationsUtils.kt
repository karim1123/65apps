package com.karimgabbasov.a65apps.utils

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.karimgabbasov.a65apps.R
import com.karimgabbasov.a65apps.ui.MainActivity

private const val ID = "id"

fun NotificationManager.sendBirthdayNotification(
    context: Context,
    id: String,
    reminderBody: String,
    intent: Intent,
    date: String?
) {
    val contentIntent = Intent(context, MainActivity::class.java).apply {
        putExtra(ID, id)
    }
    val contentPendingIntent = PendingIntent.getActivity(
        context,
        id.toInt(),
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    val builder = NotificationCompat.Builder(
        context,
        context.getString(R.string.birthday_notification_channel_id)
    )
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle(context
            .getString(R.string.notification_title))
        .setContentText(reminderBody)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
    notify(id.toInt(), builder.build())

    val alrmMgr: AlarmManager? =
        context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
    val alarmIntent =
        PendingIntent.getBroadcast(context,
            id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT)
    val timeBeforeBirthdayInMills: Long = date.countMills()
    alrmMgr?.set(AlarmManager.RTC_WAKEUP,
        timeBeforeBirthdayInMills, alarmIntent)
}



