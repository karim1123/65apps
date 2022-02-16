package com.karimgabbasov.a65apps.interactors.birthday

import com.karimgabbasov.a65apps.entity.contactmodels.DetailedContactModel
import java.util.Calendar

class BirthdayNotificationInteractorImpl(
    private val alarmManager: AlarmManagerInteractor
) : BirthdayNotificationInteractor {
    override fun setNotification(
        contact: DetailedContactModel,
        currentDate: Calendar
    ) {
        val calendar = contact.birthday.countMills(currentDate)
        alarmManager.setupAlarmManager(contact, calendar)
    }

    override fun cancelNotification(contact: DetailedContactModel) {
        alarmManager.cancelAlarmManager(contact)
    }
}
