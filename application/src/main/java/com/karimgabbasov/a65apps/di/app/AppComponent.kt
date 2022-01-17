package com.karimgabbasov.a65apps.di.app

import com.karimgabbasov.a65apps.di.api.AppComponentOwner
import com.karimgabbasov.a65apps.di.contactDetails.ContactDetailsComponent
import com.karimgabbasov.a65apps.di.contactList.ContactListComponent
import com.karimgabbasov.a65apps.di.viewModelFactory.ViewModelFactoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelFactoryModule::class])
interface AppComponent : AppComponentOwner {
    override fun plusContactListComponent(): ContactListComponent
    override fun plusContactDetailsComponent(): ContactDetailsComponent
}