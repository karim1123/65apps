package com.karimgabbasov.a65apps.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.karimgabbasov.a65apps.ContactService
import com.karimgabbasov.a65apps.FragmentOwner
import com.karimgabbasov.a65apps.R
import com.karimgabbasov.a65apps.ServiceOwner

class MainActivity : AppCompatActivity(), FragmentOwner, ServiceOwner {
    private var mService: ContactService? = null
    private var mBound: Boolean = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as ContactService.LocalBinder
            mService = binder.getService()
            mBound = true
            val contactListFragment =
                supportFragmentManager.findFragmentByTag(CONTACT_LIST_FRAGMENT)
            val contactDetailFragment =
                supportFragmentManager.findFragmentByTag(CONTACT_DETAIL_FRAGMENT)
            if (contactDetailFragment != null) {
                (contactDetailFragment as ContactDetailFragment)
                    .getContactData()
            } else if (contactListFragment != null) {
                val id: String? = intent.getStringExtra("id")//принимаем id из AlarmReceiver
                if (id != null) {
                    setContactDetailsFragment(id)//переход из уведомления на нужный фрагмент
                } else {
                    (contactListFragment as ContactListFragment)
                        .getContactData()
                }
            }
        }

        override fun onServiceDisconnected(className: ComponentName?) {
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            setContactListFragment()
        }
        Intent(this, ContactService::class.java).also { intent ->
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onDestroy() {
        if (mBound) {
            mBound = false
            unbindService(serviceConnection)
        }
        super.onDestroy()
    }

    override fun setContactListFragment() {
        supportFragmentManager.beginTransaction().add(
            R.id.fragmentContainer,
            ContactListFragment.getNewInstance(),
            CONTACT_LIST_FRAGMENT
        )
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
    }

    override fun setContactDetailsFragment(id: String) {
        supportFragmentManager.beginTransaction().replace(
            R.id.fragmentContainer,
            ContactDetailFragment.getNewInstance(id),
            CONTACT_DETAIL_FRAGMENT
        )
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
    }

    override fun getService() = mService

    companion object {
        const val CONTACT_DETAIL_FRAGMENT = "ContactDetailFragment"
        const val CONTACT_LIST_FRAGMENT = "ContactListFragment"
    }
}