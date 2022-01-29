package com.karimgabbasov.a65apps.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.karimgabbasov.a65apps.R
import com.karimgabbasov.a65apps.ui.FragmentOwner
import com.karimgabbasov.a65apps.ui.fragments.ContactDetailsFragment
import com.karimgabbasov.a65apps.ui.fragments.ContactDetailsMapFragment
import com.karimgabbasov.a65apps.ui.fragments.ContactListFragment

class MainActivity : AppCompatActivity(), FragmentOwner {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            setContactListFragment()
        }
        chekNotificationIntent()
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
            ContactDetailsFragment.getNewInstance(id),
            CONTACT_DETAIL_FRAGMENT
        )
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
    }

    override fun setContactDetailsMapFragment(id: String) {
        supportFragmentManager.beginTransaction().replace(
            R.id.fragmentContainer,
            ContactDetailsMapFragment.getNewInstance(id),
            CONTACT_DETAIL_MAP_FRAGMENT
        )
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
    }

    private fun chekNotificationIntent() {
        val id: String? = intent.getStringExtra("id")//принимаем id из AlarmReceiver
        if (id != null) {
            setContactDetailsFragment(id)//переход из уведомления на нужный фрагмент
        }
    }

    companion object {
        const val CONTACT_DETAIL_FRAGMENT = "ContactDetailFragment"
        const val CONTACT_LIST_FRAGMENT = "ContactListFragment"
        const val CONTACT_DETAIL_MAP_FRAGMENT = "ContactDetailsMapFragment"
    }
}