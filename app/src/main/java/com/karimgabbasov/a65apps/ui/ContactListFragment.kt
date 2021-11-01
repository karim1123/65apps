package com.karimgabbasov.a65apps.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.karimgabbasov.a65apps.FragmentOwner
import com.karimgabbasov.a65apps.R
import com.karimgabbasov.a65apps.ServiceOwner
import com.karimgabbasov.a65apps.data.ContactsModel
import com.karimgabbasov.a65apps.databinding.FragmentContactListBinding
import java.lang.ref.WeakReference

class ContactListFragment : Fragment() {
    private var fragmentOwner: FragmentOwner? = null
    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!
    private var contactId: Int = 34

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
        getContactData()
        binding.contactCell.root.setOnClickListener {
            fragmentOwner?.setContactDetailsFragment(contactId)
        }
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.contact_list_fragment_title)
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
                contactName.text = data.first().firstName
                phoneNumber.text = data.first().number
                contactPhoto.setImageResource(data.first().imageResourceId)
            }
        }
    }

    fun getContactData(){
        val serviceOwner = (context as? ServiceOwner)?.getService()
        serviceOwner?.getContacts(WeakReference(this))
    }

    companion object {
        fun getNewInstance(): ContactListFragment {
            return ContactListFragment()
        }
    }
}