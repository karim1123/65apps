package com.karimgabbasov.a65apps.di.route

import androidx.lifecycle.ViewModel
import com.karimgabbasov.a65apps.di.scopes.RouteScope
import com.karimgabbasov.a65apps.di.viewModelFactory.ViewModelKey
import com.karimgabbasov.a65apps.viewmodel.RouteViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class RouteViewModelModule {
    @RouteScope
    @Binds
    @IntoMap
    @ViewModelKey(RouteViewModel::class)
    abstract fun bindRouteViewModel(viewModel: RouteViewModel): ViewModel
}
