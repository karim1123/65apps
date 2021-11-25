package com.karimgabbasov.a65apps

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karimgabbasov.a65apps.data.ContactsModel
import com.karimgabbasov.a65apps.databinding.ItemContactListBinding

class ContactListItemAdapter(private val onClick: (ContactsModel) -> Unit) :
    ListAdapter<ContactsModel, ContactListItemAdapter.ContactListItemViewHolder>(ContactDiffCallback) {

    class ContactListItemViewHolder(
        binding: ItemContactListBinding,
        val onClick: (ContactsModel) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private val contactPhoto = binding.contactPhoto
        private val contactName = binding.contactName
        private val contactPhoneNumber = binding.phoneNumber
        private var currentContactsModel: ContactsModel? = null

        init {
            itemView.setOnClickListener {
                currentContactsModel?.let {
                    onClick(it)
                }
            }
        }

        /* Bind contact name, phone number and image. */
        fun bind(contactsModel: ContactsModel) {
            currentContactsModel = contactsModel
            contactName.text = contactsModel.name
            contactPhoneNumber.text = contactsModel.number
            if (contactsModel.image != null) {
                contactPhoto.setImageURI(Uri.parse(contactsModel.image))
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

    object ContactDiffCallback : DiffUtil.ItemCallback<ContactsModel>() {
        override fun areItemsTheSame(oldItem: ContactsModel, newItem: ContactsModel): Boolean {
            return (oldItem.id == newItem.id) &&
                    (oldItem.name == newItem.name) &&
                    (oldItem.number == newItem.number)
        }

        override fun areContentsTheSame(oldItem: ContactsModel, newItem: ContactsModel): Boolean {
            return oldItem == newItem
        }
    }
}
