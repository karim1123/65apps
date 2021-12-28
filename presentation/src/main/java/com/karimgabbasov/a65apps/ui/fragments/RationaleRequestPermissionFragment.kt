package com.karimgabbasov.a65apps.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.karimgabbasov.a65apps.databinding.FragmentRationaleBinding
import com.karimgabbasov.a65apps.ui.activity.MainActivity.Companion.CONTACT_DETAIL_FRAGMENT
import com.karimgabbasov.a65apps.ui.activity.MainActivity.Companion.CONTACT_LIST_FRAGMENT

class RationaleRequestPermissionFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentRationaleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRationaleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val contactListFragment =
            (activity?.supportFragmentManager?.findFragmentByTag(CONTACT_LIST_FRAGMENT) as? ContactListFragment)
        val contactDetailFragment =
            (activity?.supportFragmentManager?.findFragmentByTag(CONTACT_DETAIL_FRAGMENT) as? ContactDetailsFragment)
        binding.rationaleButton.setOnClickListener {
            if (contactDetailFragment != null) {
                contactDetailFragment.requestPermission()
            } else contactListFragment?.requestPermission()
            dismiss()
        }
    }

    companion object {
        const val TAG = "rationale_tag"
    }
}