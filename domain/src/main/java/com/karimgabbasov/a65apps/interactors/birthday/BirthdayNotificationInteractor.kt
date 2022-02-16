package com.karimgabbasov.a65apps.interactors.birthday

import com.karimgabbasov.a65apps.entity.contactmodels.DetailedContactModel
import java.util.Calendar

interface BirthdayNotificationInteractor {
    fun setNotification(
        contact: DetailedContactModel,
        currentDate: Calendar
    )

    fun cancelNotification(contact: DetailedContactModel)
}
