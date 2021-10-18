package com.karimgabbasov.a65apps

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.karimgabbasov.a65apps.databinding.FragmentContactListBinding

private const val CONTACT_ID = "id"

class ContactListFragment : Fragment() {

    private var fragmentOwner: FragmentOwner? = null
    private var contactCell: RelativeLayout? = null
    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        fragmentOwner = context as? FragmentOwner
        super.onAttach(context)
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
        contactCell = view.findViewById(R.id.contactCell)

        binding.contactCell.root.setOnClickListener {
            fragmentOwner?.setContactDetailsFragment(CONTACT_ID)
        }
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.contact_list_fragment_title)
    }

    override fun onDestroyView() {
        contactCell = null
        super.onDestroyView()
    }

    override fun onDetach() {
        fragmentOwner = null
        super.onDetach()
    }

    companion object {
        fun getNewInstance(): ContactListFragment {
            return ContactListFragment()
        }
    }
}