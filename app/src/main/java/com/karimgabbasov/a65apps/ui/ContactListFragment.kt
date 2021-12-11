package com.karimgabbasov.a65apps.ui

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.karimgabbasov.a65apps.ContactListItemAdapter
import com.karimgabbasov.a65apps.FragmentOwner
import com.karimgabbasov.a65apps.R
import com.karimgabbasov.a65apps.SimpleOffsetDrawer
import com.karimgabbasov.a65apps.data.ContactsModel
import com.karimgabbasov.a65apps.databinding.FragmentContactListBinding
import com.karimgabbasov.a65apps.di.injectViewModel
import com.karimgabbasov.a65apps.viewmodel.ContactListViewModel
import javax.inject.Inject

class ContactListFragment : Fragment(), SearchView.OnQueryTextListener {
    private var fragmentOwner: FragmentOwner? = null
    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelContactList: ContactListViewModel
    private val readContactsPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
                    // user granted permission
                    preparingContactsInfoForDisplay()
                }
                ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.READ_CONTACTS
                ) -> {
                    // user denied permission and set Don't ask again.
                    showRationaleDialog()
                }
                else -> {
                    Toast.makeText(context, R.string.denied_toast, Toast.LENGTH_SHORT).show()
                }
            }
        }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentOwner = context as? FragmentOwner
        (activity?.application as ContactApplication)
            .appComponent
            .plusContactListComponent()
            .inject(this)
        viewModelContactList = injectViewModel(factory)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelContactList = ViewModelProvider(this)[ContactListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.contact_list_fragment_title)
        requestPermission()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onDetach() {
        fragmentOwner = null
        super.onDetach()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_contacts_search, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchContacts(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchContacts(query)
        }
        return true
    }

    private fun preparingContactsInfoForDisplay() {
        val contactsAdapter =
            ContactListItemAdapter { contactsModel -> adapterOnClick(contactsModel) }
        val contactListRecyclerView = binding.contactListRecycler
        val progressIndicator = binding.contactListProgressIndicator
        contactListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        contactListRecyclerView.adapter = contactsAdapter
        contactListRecyclerView.addItemDecoration(SimpleOffsetDrawer(4))
        viewModelContactList
            .progressIndicatorStatus
            .observe(viewLifecycleOwner, {
                progressIndicator.isVisible = it
            })
        viewModelContactList
            .contactList
            .observe(viewLifecycleOwner, {
                contactsAdapter.submitList(it)
            })
        setHasOptionsMenu(true)
    }

    private fun searchContacts(query: String) {
        viewModelContactList.loadContacts(query)
    }

    private fun adapterOnClick(contactsModel: ContactsModel) {
        fragmentOwner?.setContactDetailsFragment(contactsModel.id)
    }

    private fun showRationaleDialog() {
        RationaleRequestPermissionFragment().show(
            parentFragmentManager,
            RationaleRequestPermissionFragment.TAG
        )
    }

    fun requestPermission() {
        readContactsPermission.launch(Manifest.permission.READ_CONTACTS)
    }

    companion object {
        fun getNewInstance(): ContactListFragment {
            return ContactListFragment()
        }
    }
}