package com.karimgabbasov.a65apps.di.app

import com.karimgabbasov.a65apps.di.alarm.AlarmModule
import com.karimgabbasov.a65apps.di.api.AppComponentOwner
import com.karimgabbasov.a65apps.di.contactDetails.ContactDetailsComponent
import com.karimgabbasov.a65apps.di.contactList.ContactListComponent
import com.karimgabbasov.a65apps.di.contactdetailsmap.ContactDetailsMapComponent
import com.karimgabbasov.a65apps.di.db.ContactDetailsMapModule
import com.karimgabbasov.a65apps.di.db.DatabaseModule
import com.karimgabbasov.a65apps.di.route.RouteComponent
import com.karimgabbasov.a65apps.di.schedulers.SchedulersModule
import com.karimgabbasov.a65apps.di.viewModelFactory.ViewModelFactoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class,
        ViewModelFactoryModule::class,
        AlarmModule::class,
        SchedulersModule::class,
        SchedulersModule::class,
        DatabaseModule::class,
        ContactDetailsMapModule::class
    ]
)
interface AppComponent : AppComponentOwner {
    override fun plusContactListComponent(): ContactListComponent
    override fun plusContactDetailsComponent(): ContactDetailsComponent
    override fun plusContactDetailsMapComponent(): ContactDetailsMapComponent
    override fun plusRouteComponent(): RouteComponent
}
