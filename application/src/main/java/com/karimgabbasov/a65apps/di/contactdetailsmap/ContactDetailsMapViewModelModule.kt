package com.karimgabbasov.a65apps.di.contactdetailsmap

import androidx.lifecycle.ViewModel
import com.karimgabbasov.a65apps.di.scopes.ContactDetailsMapScope
import com.karimgabbasov.a65apps.di.viewModelFactory.ViewModelKey
import com.karimgabbasov.a65apps.viewmodel.ContactDetailsMapViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ContactDetailsMapViewModelModule {
    @ContactDetailsMapScope
    @Binds
    @IntoMap
    @ViewModelKey(ContactDetailsMapViewModel::class)
    abstract fun bindContactDetailsMapViewModel(viewModel: ContactDetailsMapViewModel): ViewModel
}
