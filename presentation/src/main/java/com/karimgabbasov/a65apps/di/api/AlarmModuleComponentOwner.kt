package com.karimgabbasov.a65apps.di.api

import com.karimgabbasov.a65apps.receirvers.AlarmReceiver
import com.karimgabbasov.a65apps.utils.AlarmManagerInteractorImpl

interface AlarmModuleComponentOwner {
    fun inject(alarmManagerInteractorImpl: AlarmManagerInteractorImpl)
    fun inject(alarmReceiver: AlarmReceiver)
}
