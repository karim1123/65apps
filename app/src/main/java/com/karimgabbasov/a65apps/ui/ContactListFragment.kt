package com.karimgabbasov.a65apps.ui

import android.Manifest
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.karimgabbasov.a65apps.FragmentOwner
import com.karimgabbasov.a65apps.R
import com.karimgabbasov.a65apps.data.ContactsModel
import com.karimgabbasov.a65apps.databinding.FragmentContactListBinding
import com.karimgabbasov.a65apps.viewmodel.ContactListViewModel

class ContactListFragment : Fragment() {
    private var fragmentOwner: FragmentOwner? = null
    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!
    private lateinit var contactId: String
    private lateinit var viewModelContactList: ContactListViewModel
    private val readContactsPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
                    // user granted permission
                    viewModelContactList
                        .contactList
                        .observe(viewLifecycleOwner, { setData(it[0]) })
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentOwner = context as? FragmentOwner
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
        viewModelContactList.loadContacts(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.contactCell.root.setOnClickListener {
            fragmentOwner?.setContactDetailsFragment(contactId)
        }
        requestPermission()
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.contact_list_fragment_title)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onDetach() {
        fragmentOwner = null
        super.onDetach()
    }

    private fun setData(contactList: ContactsModel) {
        requireActivity().runOnUiThread {
            binding.contactCell.apply {
                contactName.text = contactList.name
                phoneNumber.text = contactList.number
                if (contactList.image != null) {
                    contactPhoto.setImageURI(Uri.parse(contactList.image))
                }
            }
            contactId = contactList.id
        }
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