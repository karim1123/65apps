package com.karimgabbasov.a65apps.di.api

import com.karimgabbasov.a65apps.ui.fragments.ContactListFragment

interface ContactListComponentOwner {
    fun inject(contactListFragment: ContactListFragment)
}
