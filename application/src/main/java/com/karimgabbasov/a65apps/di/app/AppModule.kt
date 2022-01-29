package com.karimgabbasov.a65apps.di.app

import android.content.Context
import com.karimgabbasov.a65apps.interactors.viewmodel.ContactDetailsInteractorImpl
import com.karimgabbasov.a65apps.interactors.viewmodel.ContactListInteractorImpl
import com.karimgabbasov.a65apps.repository.ContactsDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {
    @Singleton
    @Provides
    fun providesContext() = context

    @Provides
    fun provideContactListInteractorImpl() = ContactListInteractorImpl(ContactsDataSource(context))

    @Provides
    fun provideContactDetailsInteractorImpl() =
        ContactDetailsInteractorImpl(ContactsDataSource(context))
}