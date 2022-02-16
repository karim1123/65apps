package com.karimgabbasov.a65apps.interactors.birthday

import com.karimgabbasov.a65apps.entity.contactmodels.DetailedContactModel
import java.util.Calendar

interface AlarmManagerInteractor {
    fun setupAlarmManager(contact: DetailedContactModel, alarmDate: Calendar)
    fun cancelAlarmManager(contact: DetailedContactModel)
}
