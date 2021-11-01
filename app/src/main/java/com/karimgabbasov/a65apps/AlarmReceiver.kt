package com.karimgabbasov.a65apps

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.karimgabbasov.a65apps.ui.MainActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val reminderBody: String = intent.getStringExtra("name").toString()
        val id: Int = intent.getIntExtra("id", 0)//id фрагмента
        val date: String? = intent.getStringExtra("date")//дата дня рождения
        val contentIntent = Intent(context, MainActivity::class.java).apply {
            putExtra("id", id)
        }
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            id,
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
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(id, builder.build())
        //повторный вызов alarm manager
        val alrmMgr: AlarmManager? = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val alarmIntent =
            PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val timeBeforeBirthdayInMills: Long = date.countMills()
        alrmMgr?.set(AlarmManager.RTC_WAKEUP,
            timeBeforeBirthdayInMills, alarmIntent)
    }
}