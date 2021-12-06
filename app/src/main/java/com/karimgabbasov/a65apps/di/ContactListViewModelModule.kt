package com.karimgabbasov.a65apps.di

import androidx.lifecycle.ViewModel
import com.karimgabbasov.a65apps.viewmodel.ContactListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ContactListViewModelModule {

    @ContactListScope
    @Binds
    @IntoMap
    @ViewModelKey(ContactListViewModel::class)
    abstract fun bindContactDetailsViewModel(viewModel: ContactListViewModel): ViewModel
}