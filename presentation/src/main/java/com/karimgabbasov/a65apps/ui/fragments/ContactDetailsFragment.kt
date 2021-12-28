package com.karimgabbasov.a65apps.ui.fragments

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
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.karimgabbasov.a65apps.R
import com.karimgabbasov.a65apps.databinding.FragmentContactDetailBinding
import com.karimgabbasov.a65apps.di.api.AppContainer
import com.karimgabbasov.a65apps.di.injectViewModel
import com.karimgabbasov.a65apps.entity.contactmodels.DetailedContactModel
import com.karimgabbasov.a65apps.utils.AlarmUtils
import com.karimgabbasov.a65apps.utils.checkNotificationSwitchStatusUtil
import com.karimgabbasov.a65apps.viewmodel.ContactDetailsViewModel
import javax.inject.Inject

class ContactDetailsFragment : Fragment() {
    private var _binding: FragmentContactDetailBinding? = null
    private val binding get() = _binding!!
    private var contactName: String? = null
    private lateinit var contactId: String
    private var contactBirthday: String? = null
    private lateinit var viewModelContactDetails: ContactDetailsViewModel
    private val readContactsPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
                    // user granted permission
                    preparingContactInfoForDisplay()
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
        contactId = arguments?.getString(ARGUMENT_ID).toString()
        (activity?.application as AppContainer).getAppComponent()
            ?.plusContactDetailsComponent()
            ?.inject(this)
        viewModelContactDetails = injectViewModel(factory)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentContactDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermission()
        binding.switchNotification.checkNotificationSwitchStatusUtil(
            requireContext(),
            contactId
        )//проверка состояния switch
        binding.switchNotification.setOnClickListener { //обработка нажатий switch
            if (binding.switchNotification.isChecked) {
                if (contactBirthday != EMPTY_BIRTHDAY) {
                    AlarmUtils.setupAlarm(requireContext(), contactName, contactId, contactBirthday)
                } else {
                    Toast.makeText(context, R.string.denied_alarm_toast, Toast.LENGTH_SHORT).show()
                    binding.switchNotification.isChecked = false
                }
            } else {
                AlarmUtils.cancelAlarm(requireContext(), contactId)
            }
        }
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.contact_detail_fragment_title)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun preparingContactInfoForDisplay() {
        val progressIndicator = binding.contactDetailsProgressIndicator
        viewModelContactDetails.loadDetailContact(contactId)
        viewModelContactDetails
            .progressIndicatorStatus
            .observe(viewLifecycleOwner, {
                progressIndicator.isVisible = it
            })
        viewModelContactDetails
            .contactDetails
            .observe(viewLifecycleOwner, { setData(it[0]) })
    }

    private fun setData(detailedContact: DetailedContactModel) {
        requireActivity().runOnUiThread {
            binding.apply {
                contactName.text = detailedContact.name
                phoneNumber.text = detailedContact.number
                secondPhoneNumber.text = detailedContact.secondPhoneNumber
                mail.text = detailedContact.mail
                secondMail.text = detailedContact.secondMail
                description.text = detailedContact.description
                birthday.text = detailedContact.birthday
                if (detailedContact.image != null) {
                    contactPhoto.setImageURI(Uri.parse(detailedContact.image))
                }
            }
            contactName = detailedContact.name
            contactBirthday = detailedContact.birthday
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
        private const val ARGUMENT_ID = "id"
        private const val EMPTY_BIRTHDAY = "-"
        fun getNewInstance(id: String): ContactDetailsFragment =
            ContactDetailsFragment().apply {
                arguments = bundleOf(
                    ARGUMENT_ID to id
                )
            }
    }
}

