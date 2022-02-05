package com.karimgabbasov.a65apps.di.contactDetails

import com.karimgabbasov.a65apps.di.api.ContactDetailsComponentOwner
import com.karimgabbasov.a65apps.di.scopes.ContactDetailsScope
import com.karimgabbasov.a65apps.ui.fragments.ContactDetailsFragment
import dagger.Subcomponent

@ContactDetailsScope
@Subcomponent(modules = [ContactDetailsViewModelModule::class])
interface ContactDetailsComponent : ContactDetailsComponentOwner {
    override fun inject(contactDetailsFragment: ContactDetailsFragment)
}
