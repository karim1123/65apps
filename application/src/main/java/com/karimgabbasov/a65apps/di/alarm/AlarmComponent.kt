package com.karimgabbasov.a65apps.di.alarm

import com.karimgabbasov.a65apps.di.api.AlarmModuleComponentOwner
import com.karimgabbasov.a65apps.receirvers.AlarmReceiver
import com.karimgabbasov.a65apps.utils.AlarmManagerInteractorImpl
import dagger.Subcomponent

@Subcomponent(modules = [AlarmModule::class])
interface AlarmComponent: AlarmModuleComponentOwner {
    override fun inject(alarmManagerInteractorImpl: AlarmManagerInteractorImpl)
    override fun inject(alarmReceiver: AlarmReceiver)
}