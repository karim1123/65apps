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
import com.karimgabbasov.a65apps.FragmentOwner
import com.karimgabbasov.a65apps.R
import com.karimgabbasov.a65apps.RationaleRequestPermissionFragment
import com.karimgabbasov.a65apps.ServiceOwner
import com.karimgabbasov.a65apps.data.ContactsModel
import com.karimgabbasov.a65apps.databinding.FragmentContactListBinding
import java.lang.ref.WeakReference

class ContactListFragment : Fragment() {
    private var fragmentOwner: FragmentOwner? = null
    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!
    private lateinit var contactId: String
    private val readContactsPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
                    // user granted permission
                    getContactData()
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

    fun setData(data: List<ContactsModel>) {
        requireActivity().runOnUiThread {
            binding.contactCell.apply {
                contactName.text = data.first().name
                phoneNumber.text = data.first().number
                if (data.first().image != null){
                    contactPhoto.setImageURI(Uri.parse(data.first().image))
                }
            }
            contactId = data.first().id
        }
    }

    fun getContactData() {
        val serviceOwner = (context as? ServiceOwner)?.getService()
        serviceOwner?.getContacts(WeakReference(this), requireContext())
    }

    private fun showRationaleDialog() {
        RationaleRequestPermissionFragment().show(parentFragmentManager,
            RationaleRequestPermissionFragment.TAG)
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