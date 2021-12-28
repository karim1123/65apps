package com.karimgabbasov.a65apps.di.app

import android.app.Application
import com.karimgabbasov.a65apps.di.api.AppComponentOwner
import com.karimgabbasov.a65apps.di.api.AppContainer

class ContactApplication : Application(), AppContainer {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    override fun getAppComponent(): AppComponentOwner {
        return appComponent
    }
}