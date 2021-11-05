package com.karimgabbasov.a65apps

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.karimgabbasov.a65apps.data.ContactsModel
import com.karimgabbasov.a65apps.data.DetailedContactModel
import com.karimgabbasov.a65apps.ui.ContactDetailFragment
import com.karimgabbasov.a65apps.ui.ContactListFragment
import java.lang.ref.WeakReference
import kotlin.concurrent.thread

class ContactService : Service() {
    private val binder = LocalBinder()

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    fun getContacts(contactListFragment: WeakReference <ContactListFragment>) {
        thread(start = true) {
            Thread.sleep(2000)
            contactListFragment.get()?.setData(
                listOf(ContactsModel(firstName = "Karim",
                    number = "891233334565",
                    imageResourceId = R.drawable.first_contact_photo))
            )
        }
    }

    fun getDetailContact(contactDetailFragment: WeakReference <ContactDetailFragment>) {
        thread(start = true) {
            Thread.sleep(2000)
            contactDetailFragment.get()?.setData(
                listOf(DetailedContactModel(firstName = "Karim",
                    number = "891233334565",
                    secondPhoneNumber = "89121111111",
                    mail = "karim@mail.ru",
                    secondMail = "karim2@yandex.ru",
                    description = "Gabbasov Karim Ramilevich",
                    birthday = "29-11-2000",
                    imageResourceId = R.drawable.first_contact_photo))
            )
        }
    }

    inner class LocalBinder : Binder() {
        fun getService(): ContactService = this@ContactService
    }
}