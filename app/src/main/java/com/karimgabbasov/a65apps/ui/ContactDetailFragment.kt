package com.karimgabbasov.a65apps.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.karimgabbasov.a65apps.ContactService
import com.karimgabbasov.a65apps.R
import com.karimgabbasov.a65apps.ServiceOwner
import com.karimgabbasov.a65apps.data.DetailedContactModel
import com.karimgabbasov.a65apps.databinding.FragmentContactDetailBinding
import java.lang.ref.WeakReference

class ContactDetailFragment : Fragment() {
    private var service: ContactService? = null
    private var _binding: FragmentContactDetailBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        service = (context as? ServiceOwner)?.getService()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getString(ARGUMENT_ID)
        service?.getDetailContact(WeakReference(this))
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.contact_detail_fragment_title)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onDetach() {
        service = null
        super.onDetach()
    }

    fun setData(data: List<DetailedContactModel>) {
        requireActivity().runOnUiThread {
            binding.apply {
                contactName.text = data.first().firstName
                phoneNumber.text = data.first().number
                secondPhoneNumber.text = data.first().secondPhoneNumber
                mail.text = data.first().mail
                secondMail.text = data.first().secondMail
                description.text = data.first().description
                contactPhoto.setImageResource(data.first().imageResourceId)
            }
        }
    }

    fun getContactData(){
        val serviceOwner = (context as? ServiceOwner)?.getService()
        serviceOwner?.getDetailContact(WeakReference(this))
    }

    companion object {
        private const val ARGUMENT_ID = "id"
        fun getNewInstance(id: String): ContactDetailFragment =
            ContactDetailFragment().apply {
                arguments = bundleOf(
                    ARGUMENT_ID to id
                )
            }
    }
}