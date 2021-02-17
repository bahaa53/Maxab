package com.task.maxab.domain.repositories

import androidx.lifecycle.LiveData
import com.task.maxab.data.repositories.AddContactRepositoryImp
import com.task.maxab.data.repositories.ContactsRepositoryImp
import com.task.maxab.domain.entities.Contact


val addContactsRepositoryLazy by lazy { AddContactRepositoryImp() }

interface AddContactsRepository {

    val addContactResponseResult: LiveData<Result<Boolean>>

    suspend fun addContact(name: String, phone: String): LiveData<Result<Boolean>>
}