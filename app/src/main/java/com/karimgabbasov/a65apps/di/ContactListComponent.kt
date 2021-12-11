package com.karimgabbasov.a65apps.di

import com.karimgabbasov.a65apps.ui.ContactListFragment
import dagger.Subcomponent

@ContactListScope
@Subcomponent(modules = [ContactListViewModelModule::class])
interface ContactListComponent {
    fun inject(contactListFragment: ContactListFragment)
}