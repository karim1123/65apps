package com.karimgabbasov.a65apps

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.provider.ContactsContract
import com.karimgabbasov.a65apps.ui.ContactDetailFragment
import com.karimgabbasov.a65apps.ui.ContactListFragment
import java.lang.ref.WeakReference
import kotlin.concurrent.thread

class ContactService : Service() {
    private val binder = LocalBinder()

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    fun getContacts(contactListFragment: WeakReference<ContactListFragment>, context: Context) {
        thread(start = true) {
            Thread.sleep(2000)
            contactListFragment.get()?.setData(
                ContactsDataSource.getContactListData(context)
            )
        }
    }

    fun getDetailContact(contactDetailFragment: WeakReference<ContactDetailFragment>, context: Context, id: String) {
        thread(start = true) {
            Thread.sleep(2000)
            contactDetailFragment.get()?.setData(
                ContactsDataSource.getContactData(context,id)
            )
        }
    }

    inner class LocalBinder : Binder() {
        fun getService(): ContactService = this@ContactService
    }
}