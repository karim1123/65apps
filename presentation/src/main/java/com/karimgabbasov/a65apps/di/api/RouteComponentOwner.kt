package com.karimgabbasov.a65apps.di.api

import com.karimgabbasov.a65apps.ui.fragments.RouteFragment

interface RouteComponentOwner {
    fun inject(routeFragment: RouteFragment)
}