package com.karimgabbasov.a65apps.ui

import android.app.Application
import com.karimgabbasov.a65apps.di.AppComponent
import com.karimgabbasov.a65apps.di.AppModule
import com.karimgabbasov.a65apps.di.DaggerAppComponent

class ContactApplication: Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}