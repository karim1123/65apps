package com.karimgabbasov.a65apps.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelFactoryModule::class])
interface AppComponent {
    fun plusContactListComponent(): ContactListComponent
    fun plusContactDetailsComponent(): ContactDetailsComponent
}