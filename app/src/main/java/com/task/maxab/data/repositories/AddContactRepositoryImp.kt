package com.task.maxab.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.task.maxab.R
import com.task.maxab.data.network.firebaseDBLazy
import com.task.maxab.data.network.isOnline
import com.task.maxab.domain.entities.Contact
import com.task.maxab.domain.repositories.AddContactsRepository
import com.task.maxab.domain.repositories.ContactsRepository
import com.task.maxab.domain.repositories.Result
import com.task.maxab.presentation.MyApp

const val NAME_PARAM = "Name"
const val PHONE_PARAM = "Phone"

class AddContactRepositoryImp(
    private val context: Context = MyApp.applicationContext(),
    private val firebaseStoreDB: FirebaseFirestore = firebaseDBLazy
) :
    AddContactsRepository {

    private var _addContactResponseResult =
        MutableLiveData<Result<Boolean>>()

    override val addContactResponseResult: LiveData<Result<Boolean>>
        get() = _addContactResponseResult

    override suspend fun addContact(name: String, phone: String): LiveData<Result<Boolean>> {

        if (!isOnline(context)) {
            _addContactResponseResult.value =
                Result.Error(message = context.getString(R.string.no_internet_connection))
        } else {
            val contact = hashMapOf(
                NAME_PARAM to name,
                PHONE_PARAM to phone
            )

            firebaseStoreDB.collection(COLLECTION_PATH)
                .add(contact)
                .addOnSuccessListener { documentReference ->
                    _addContactResponseResult.value =
                        Result.Success(true)
                }
                .addOnFailureListener { e ->
                    _addContactResponseResult.value =
                        Result.Error(message = e.message ?: "")
                }
        }

        return addContactResponseResult
    }
}