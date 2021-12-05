package com.karimgabbasov.a65apps.di

import androidx.lifecycle.ViewModel
import com.karimgabbasov.a65apps.viewmodel.ContactDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ContactDetailsViewModelModule {

    @ContactDetailsScope
    @Binds
    @IntoMap
    @ViewModelKey(ContactDetailViewModel::class)
    abstract fun bindContactDetailsViewModel(viewModel: ContactDetailViewModel): ViewModel
}