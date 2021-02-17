package com.task.maxab.presentation.contactsStory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.task.maxab.domain.usecase.ContactsUseCase

class ContactsViewModelFactory(private val useCase: ContactsUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactsViewModel::class.java)) {
            return ContactsViewModel(
                useCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}