package com.task.maxab.presentation.contactsStory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.maxab.domain.entities.Contact

class ContactsAdapter(val listener: (Contact) -> Unit) :
    RecyclerView.Adapter<ContactsViewHolder>() {

    private var contacts: List<Contact> = listOf()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ContactsViewHolder(
            inflater,
            parent
        )
    }

    public fun setContactsData(contacts: List<Contact>){
        this.contacts = contacts
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bindContact(contacts.get(position) , listener)
    }

    override fun getItemCount(): Int {
        return contacts.size ?: 0
    }
}