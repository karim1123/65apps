package com.karimgabbasov.a65apps.di.contactdetailsmap

import android.content.Context
import com.karimgabbasov.a65apps.di.scopes.ContactDetailsMapScope
import com.karimgabbasov.a65apps.entity.map.ContactAddress
import com.karimgabbasov.a65apps.utils.ContactAddressImpl
import dagger.Module
import dagger.Provides

@Module
class ContactAddressModule() {
    @ContactDetailsMapScope
    @Provides
    fun provideContactAddress(context: Context): ContactAddress = ContactAddressImpl(context)
}