package com.karimgabbasov.a65apps.di.api

interface AppComponentOwner {
    fun plusContactListComponent(): ContactListComponentOwner
    fun plusContactDetailsComponent(): ContactDetailsComponentOwner
}