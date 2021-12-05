package com.karimgabbasov.a65apps.di

import com.karimgabbasov.a65apps.ui.ContactDetailFragment
import dagger.Subcomponent

@ContactDetailsScope
@Subcomponent(modules = [ContactDetailsViewModelModule::class])
interface ContactDetailsComponent {
    fun inject(contactDetailFragment: ContactDetailFragment)
}