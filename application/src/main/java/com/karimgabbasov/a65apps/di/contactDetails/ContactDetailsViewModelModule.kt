package com.karimgabbasov.a65apps.di.contactDetails

import androidx.lifecycle.ViewModel
import com.karimgabbasov.a65apps.di.scopes.ContactDetailsScope
import com.karimgabbasov.a65apps.di.viewModelFactory.ViewModelKey
import com.karimgabbasov.a65apps.viewmodel.ContactDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ContactDetailsViewModelModule {
    @ContactDetailsScope
    @Binds
    @IntoMap
    @ViewModelKey(ContactDetailsViewModel::class)
    abstract fun bindContactDetailsViewModel(viewModel: ContactDetailsViewModel): ViewModel
}
