package com.karimgabbasov.a65apps.di.contactdetailsmap

import com.karimgabbasov.a65apps.di.api.ContactDetailsMapComponentOwner
import com.karimgabbasov.a65apps.di.scopes.ContactDetailsMapScope
import com.karimgabbasov.a65apps.ui.fragments.ContactDetailsMapFragment
import dagger.Subcomponent

@ContactDetailsMapScope
@Subcomponent(
    modules = [
        ContactDetailsMapViewModelModule::class,
        DatabaseModule::class,
        ContactDetailsMapModule::class,
        ContactAddressModule::class]
)
interface ContactDetailsMapComponent : ContactDetailsMapComponentOwner {
    override fun inject(contactDetailsMapFragment: ContactDetailsMapFragment)
}