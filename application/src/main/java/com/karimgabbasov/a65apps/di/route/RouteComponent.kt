package com.karimgabbasov.a65apps.di.route

import com.karimgabbasov.a65apps.di.api.RouteComponentOwner
import com.karimgabbasov.a65apps.di.scopes.RouteScope
import com.karimgabbasov.a65apps.ui.fragments.RouteFragment
import dagger.Subcomponent

@RouteScope
@Subcomponent(modules = [RouteViewModelModule::class, RouteModule::class])
interface RouteComponent : RouteComponentOwner {
    override fun inject(routeFragment: RouteFragment)
}