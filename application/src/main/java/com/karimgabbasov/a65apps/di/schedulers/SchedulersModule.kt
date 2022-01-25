package com.karimgabbasov.a65apps.di.schedulers

import com.karimgabbasov.a65apps.viewmodel.ContactDetailsViewModel
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Named

@Module
object SchedulersModule {
    @Provides
    @Named(ContactDetailsViewModel.observeOnSchedulerQualifier)
    fun provideObserveOnScheduler() = AndroidSchedulers.mainThread()

    @Provides
    @Named(ContactDetailsViewModel.subscribeOnSchedulerQualifier)
    fun provideSubscribeOnScheduler() = Schedulers.io()
}