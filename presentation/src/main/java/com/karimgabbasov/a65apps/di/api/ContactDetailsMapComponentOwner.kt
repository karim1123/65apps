package com.karimgabbasov.a65apps.di.api

import com.karimgabbasov.a65apps.ui.fragments.ContactDetailsMapFragment

interface ContactDetailsMapComponentOwner {
    fun inject(contactDetailsMapFragment: ContactDetailsMapFragment)
}
