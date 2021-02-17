package com.task.maxab.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.task.maxab.data.network.firebaseDBLazy
import com.task.maxab.domain.entities.Contact
import com.task.maxab.domain.repositories.ContactsRepository
import com.task.maxab.domain.repositories.Result
import com.task.maxab.presentation.MyApp


const val COLLECTION_PATH = "Contacts"

class ContactsRepositoryImp(
    private val context: Context = MyApp.applicationContext(),
    private val firebaseStoreDB: FirebaseFirestore = firebaseDBLazy
) :
    ContactsRepository {

    private var _contactsResponseResult =
        MutableLiveData<Result<List<Contact>>>()

    override val getAllContactsResponseResult: LiveData<Result<List<Contact>>>
        get() = _contactsResponseResult

    override suspend fun getAllContacts(): LiveData<Result<List<Contact>>> {
        firebaseStoreDB.collection(COLLECTION_PATH)
            .get()
            .addOnSuccessListener { result ->
                val contactsList = mutableListOf<Contact>()
                for (document in result) {
                    val name: String = document.data.getValue("Name").toString()
                    val phone = document.data.getValue("Phone").toString()
                    val contact = Contact(name, phone)
                    contactsList.add(contact)
                }

                _contactsResponseResult.value =
                    Result.Success(contactsList)
            }
            .addOnFailureListener { exception ->
                _contactsResponseResult.value =
                    Result.Error(message = exception.message ?: "")
            }

        return getAllContactsResponseResult
    }
}