package com.karimgabbasov.a65apps.di

import android.content.Context
import com.karimgabbasov.a65apps.model.ContactsDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {
    @Singleton
    @Provides
    fun providesContext() = context

    @Singleton
    @Provides
    fun providesRepository() = ContactsDataSource(context)
}