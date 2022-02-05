package com.karimgabbasov.a65apps.di.alarm

import android.app.AlarmManager
import android.content.Context
import com.karimgabbasov.a65apps.interactors.birthday.AlarmManagerInteractor
import com.karimgabbasov.a65apps.interactors.birthday.BirthdayNotificationInteractor
import com.karimgabbasov.a65apps.interactors.birthday.BirthdayNotificationInteractorImpl
import com.karimgabbasov.a65apps.utils.AlarmManagerInteractorImpl
import dagger.Module
import dagger.Provides

@Module
class AlarmModule {
    @Provides
    fun provideAlarmManager(context: Context) =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    @Provides
    fun provideAlarmManagerInteractorImpl(
        context: Context,
        alarmManager: AlarmManager
    ): AlarmManagerInteractor =
        AlarmManagerInteractorImpl(context, alarmManager)

    @Provides
    fun provideBirthdayNotificationInteractorImpl(
        alarmManagerInteractorImpl: AlarmManagerInteractor
    ): BirthdayNotificationInteractor =
        BirthdayNotificationInteractorImpl(alarmManagerInteractorImpl)
}
