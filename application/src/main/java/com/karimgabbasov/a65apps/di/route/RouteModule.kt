package com.karimgabbasov.a65apps.di.route

import android.content.Context
import com.karimgabbasov.a65apps.di.scopes.RouteScope
import com.karimgabbasov.a65apps.entity.map.PolylineInteractor
import com.karimgabbasov.a65apps.entity.map.PolylineInteractorImpl
import com.karimgabbasov.a65apps.utils.RouteUtils
import com.karimgabbasov.a65apps.utils.RouteUtilsImpl
import dagger.Module
import dagger.Provides

@Module
class RouteModule {
    @RouteScope
    @Provides
    fun providePolylineInteractorImpl(): PolylineInteractor = PolylineInteractorImpl()

    @RouteScope
    @Provides
    fun provideRouteUtilsImpl(
        context: Context,
        polylineInteractorImpl: PolylineInteractor
    ): RouteUtils =
        RouteUtilsImpl(context, polylineInteractorImpl)
}