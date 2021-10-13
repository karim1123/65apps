package com.karimgabbasov.a65apps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf

class ContactDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = inflater.inflate(R.layout.fragment_contact_detail, null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getString(ARGUMENT_ID)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.contact_detail_fragment_title)
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