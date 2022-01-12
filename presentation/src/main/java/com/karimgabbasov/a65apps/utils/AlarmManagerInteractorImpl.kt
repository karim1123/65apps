package com.karimgabbasov.a65apps.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.karimgabbasov.a65apps.entity.contactmodels.DetailedContactModel
import com.karimgabbasov.a65apps.interactors.birthday.AlarmManagerInteractor
import com.karimgabbasov.a65apps.receirvers.AlarmReceiver
import java.util.*
import javax.inject.Inject

private const val NAME = "name"
private const val ID = "id"
private const val DATE = "date"

class AlarmManagerInteractorImpl @Inject constructor(
    private val context: Context,
    private val alarmManager: AlarmManager
) :
    AlarmManagerInteractor {
    override fun setupAlarmManager(
        contact: DetailedContactModel,
        alarmDate: Calendar
    ) { //функция для вызова alarm manager
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(NAME, contact.name)
            putExtra(ID, contact.id)
            putExtra(DATE, contact.birthday)
        }
        val existingIntent = createPendingIntent(contact, intent, PendingIntent.FLAG_NO_CREATE)
        if (existingIntent == null) {
            val alarmIntent =
                createPendingIntent(contact, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                alarmDate.timeInMillis, alarmIntent
            )
        }
    }

    override fun cancelAlarmManager(contact: DetailedContactModel) { //функция для отмены alarm manager
        val intent = Intent(context, AlarmReceiver::class.java)
        val alarmIntent = createPendingIntent(contact, intent, 0)
        alarmManager.cancel(alarmIntent)
        alarmIntent.cancel()
    }

    private fun createPendingIntent(contact: DetailedContactModel, intent: Intent, flag: Int) =
        PendingIntent.getBroadcast(context, contact.id.toInt(), intent, flag)
}