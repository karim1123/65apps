package com.karimgabbasov.a65apps.di.viewModelFactory

import androidx.lifecycle.ViewModelProvider
import com.karimgabbasov.a65apps.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
