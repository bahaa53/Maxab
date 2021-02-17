package com.task.maxab.presentation.contactsStory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.task.maxab.domain.entities.Contact
import com.task.maxab.domain.repositories.Result
import com.task.maxab.domain.usecase.ContactsUseCase

class ContactsViewModel(private val contactsUseCase: ContactsUseCase) : ViewModel() {

    suspend fun getAllContacts(): LiveData<Result<List<Contact>>> {
        return contactsUseCase.getAlllContacts()
    }

    suspend fun addContacts(name: String, phone: String): LiveData<Result<Boolean>> {
        return contactsUseCase.addContact(name , phone)
    }

    fun filterContacts(contacts: List<Contact>, it: String): List<Contact> {
        return contactsUseCase.filterContacts(contacts, it)
    }

    fun isUniqueContact(
        contacts: List<Contact>,
        name: String,
        phone: String
    ): Boolean {
        return contactsUseCase.isUniqueContact(contacts, name, phone)
    }
}
