package com.task.maxab.presentation.contactsStory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.maxab.R
import com.task.maxab.domain.entities.Contact
import kotlinx.android.synthetic.main.item_contact_layout.view.*

class ContactsViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(
        inflater.inflate(
            R.layout.item_contact_layout,
            parent,
            false
        )
    ) {

    fun bindContact(
        contact: Contact,
        listener: (Contact) -> Unit
    ) {
        with(itemView) {
            tv_contact_symbol.text = contact.Name?.get(0)?.toUpperCase()?.toString() ?: ""
            tv_contact_name.text = contact.Name
            tv_contact_number.text = contact.Phone

            setOnClickListener {
                listener(contact)
            }
        }
    }
}