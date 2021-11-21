package com.karimgabbasov.a65apps.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.karimgabbasov.a65apps.AlarmReceiver
import com.karimgabbasov.a65apps.R

private const val NAME = "name"
private const val ID = "id"
private const val DATE = "date"

object AlarmUtils {
    fun setupAlarm(context: Context, contactName: String?, contactId: String, contactBirthday: String?) { //функция для вызова alarm manager
        val alrmMgr: AlarmManager? =
            context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        val notificationBody = context.getString(R.string.today_birthday, contactName)
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(NAME, notificationBody)
            putExtra(ID, contactId)
            putExtra(DATE, contactBirthday)
        }
        val existingIntent =
            PendingIntent.getBroadcast(context,
                contactId.toInt(),
                intent,
                PendingIntent.FLAG_NO_CREATE)
        if (existingIntent == null) {
            val alarmIntent = PendingIntent.getBroadcast(context,
                contactId.toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT)
            val timeBeforeBirthdayInMills: Long = contactBirthday.countMills()

            alrmMgr?.set(AlarmManager.RTC_WAKEUP,
                timeBeforeBirthdayInMills, alarmIntent)
        }
    }

    fun cancelAlarm(context: Context, contactId: String) { //функция для отмены alarm manager
        val alrmMgr: AlarmManager? = null
        val intent = Intent(context, AlarmReceiver::class.java)
        val alarmIntent = PendingIntent.getBroadcast(context, contactId.toInt(), intent, 0)
        alrmMgr?.cancel(alarmIntent)
        alarmIntent.cancel()
    }
}