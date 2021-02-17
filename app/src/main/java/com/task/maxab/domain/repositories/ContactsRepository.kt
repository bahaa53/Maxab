package com.task.maxab.domain.repositories

import androidx.lifecycle.LiveData
import com.task.maxab.data.repositories.ContactsRepositoryImp
import com.task.maxab.domain.entities.Contact


val contactsRepositoryLazy by lazy { ContactsRepositoryImp() }

interface ContactsRepository {

    val getAllContactsResponseResult: LiveData<Result<List<Contact>>>

    suspend fun getAllContacts(): LiveData<Result<List<Contact>>>
}