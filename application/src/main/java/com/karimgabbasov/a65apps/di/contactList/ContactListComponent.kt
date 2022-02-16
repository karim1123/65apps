package com.karimgabbasov.a65apps.di.contactList

import com.karimgabbasov.a65apps.di.api.ContactListComponentOwner
import com.karimgabbasov.a65apps.di.scopes.ContactListScope
import com.karimgabbasov.a65apps.ui.fragments.ContactListFragment
import dagger.Subcomponent

@ContactListScope
@Subcomponent(modules = [ContactListViewModelModule::class])
interface ContactListComponent : ContactListComponentOwner {
    override fun inject(contactListFragment: ContactListFragment)
}
