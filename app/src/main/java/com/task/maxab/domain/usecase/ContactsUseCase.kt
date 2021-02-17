package com.task.maxab.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.task.maxab.domain.entities.Contact
import com.task.maxab.domain.repositories.*
import kotlinx.android.synthetic.main.activity_contacts_layout.*

class ContactsUseCase(
    private val contactsRepository: ContactsRepository = contactsRepositoryLazy,
    private val addContactsRepository: AddContactsRepository = addContactsRepositoryLazy
) {

    private var _contactsResponseResult =
        MutableLiveData<Result<List<Contact>>>()

    private val getAllContactsResponseResult: LiveData<Result<List<Contact>>>
        get() = _contactsResponseResult

    suspend fun getAlllContacts(): LiveData<Result<List<Contact>>> {
        val result = contactsRepository.getAllContacts()
        result.observeForever {
            when (it) {
                is Result.Success<List<Contact>> -> {
                    it.data?.let {
                        val sortedList = it.sortedBy {
                            it.Name
                        }
                        _contactsResponseResult.value =
                            Result.Success(sortedList)
                    }
                }

                is Result.Error -> {
                    _contactsResponseResult.value =
                        Result.Error(message = it.message)
                }
            }
        }

        return getAllContactsResponseResult
    }

    fun filterContacts(contacts: List<Contact>, searchText: String): List<Contact> {

        val filterContacts = contacts.filter {
            (it.Name?.toLowerCase() ?: "").contains(searchText.toLowerCase()) || (it.Phone
                ?: "").contains(searchText)
        }
        return filterContacts
    }

    fun isUniqueContact(contactsData: List<Contact>, name: String, phone: String): Boolean {
        val filteredContacts = contactsData.filter {
            (it.Name?.toLowerCase() ?: "").equals(name.toLowerCase()) || (it.Phone
                ?: "").equals(phone)
        }
        return filteredContacts.isEmpty()
    }

    suspend fun addContact(name: String, phone: String): LiveData<Result<Boolean>> {
        val modifiedName = name.replace(name.get(0), name.get(0).toUpperCase())
        return addContactsRepository.addContact(modifiedName, phone)
    }
}