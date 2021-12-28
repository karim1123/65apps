package com.karimgabbasov.a65apps.di.api

import com.karimgabbasov.a65apps.ui.fragments.ContactDetailsFragment

interface ContactDetailsComponentOwner {
    fun inject(contactDetailsFragment: ContactDetailsFragment)
}
