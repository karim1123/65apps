package com.karimgabbasov.a65apps.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karimgabbasov.a65apps.databinding.ItemContactListBinding
import com.karimgabbasov.a65apps.entity.contactmodels.ContactsListModel

class ContactListItemAdapter(private val onClick: (ContactsListModel) -> Unit) :
    ListAdapter<ContactsListModel, ContactListItemAdapter.ContactListItemViewHolder>(
        ContactDiffCallback
    ) {

    class ContactListItemViewHolder(
        binding: ItemContactListBinding,
        val onClick: (ContactsListModel) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private val contactPhoto = binding.contactPhoto
        private val contactName = binding.contactName
        private val contactPhoneNumber = binding.phoneNumber
        private var currentContactsListModel: ContactsListModel? = null

        init {
            itemView.setOnClickListener {
                currentContactsListModel?.let {
                    onClick(it)
                }
            }
        }

        /* Bind contact name, phone number and image. */
        fun bind(contactsListModel: ContactsListModel) {
            currentContactsListModel = contactsListModel
            contactName.text = contactsListModel.name
            contactPhoneNumber.text = contactsListModel.number
            if (contactsListModel.image != null) {
                contactPhoto.setImageURI(Uri.parse(contactsListModel.image))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemContactListBinding.inflate(inflater, parent, false)
        return ContactListItemViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ContactListItemViewHolder, position: Int) {
        val contact = getItem(position)
        holder.bind(contact)
    }

    object ContactDiffCallback : DiffUtil.ItemCallback<ContactsListModel>() {
        override fun areItemsTheSame(
            oldItem: ContactsListModel,
            newItem: ContactsListModel
        ): Boolean {
            return (oldItem.id == newItem.id) &&
                    (oldItem.name == newItem.name) &&
                    (oldItem.number == newItem.number)
        }

        override fun areContentsTheSame(
            oldItem: ContactsListModel,
            newItem: ContactsListModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}
