package com.task.maxab.presentation.contactsStory

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.task.maxab.R
import com.task.maxab.domain.entities.Contact
import com.task.maxab.domain.repositories.Result
import com.task.maxab.presentation.isValidPhone
import kotlinx.android.synthetic.main.activity_add_contacts_layout.*
import kotlinx.android.synthetic.main.activity_contacts_layout.*
import kotlinx.android.synthetic.main.add_contacts_custom_toolbar.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

const val DELAY_TIME = 2000L

class AddContactsActivity : BaseContactsActivity() {

    private lateinit var contactsData: ArrayList<Contact>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_add_contacts_layout)
        job = Job()
        setupViewModel()
        getExtraData()
        handleClickActions()
    }

    private fun getExtraData() {
        contactsData =
            intent.getParcelableArrayListExtra<Contact>(INTENT_CONTACTS_LIST_PARAM) ?: arrayListOf()
    }

    private fun handleClickActions() {
        tv_save_contact.setOnClickListener {
            checkContactValidation()
        }

        iv_close.setOnClickListener {
            finish()
        }
    }

    private fun checkContactValidation() {
        val name = et_contact_name.text.toString().trim()
        val phone = et_contact_phone.text.toString().trim()
        if (name.isEmpty() || phone.isEmpty()) {
            displaySnackBar(getString(R.string.fill_all_fields))
        } else if (!phone.isValidPhone()) {
            displaySnackBar(getString(R.string.enter_valid_phone_number))
        } else if (!isUniqueContact(name, phone)) {
            displaySnackBar(getString(R.string.name_not_unique))
        } else {
            showProgressDialog()
            addContact(name, phone)
        }
    }

    private fun isUniqueContact(name: String, phone: String): Boolean {
        return contactsViewModel.isUniqueContact(contactsData, name, phone)
    }

    private fun addContact(name: String, phone: String) = launch {
        val addContactResponse = contactsViewModel.addContacts(name, phone)
        observeForAddContact(addContactResponse)
    }

    private fun observeForAddContact(forgetPasswordResponse: LiveData<Result<Boolean>>) {
        forgetPasswordResponse.observe(
            this@AddContactsActivity,
            Observer<Result<Boolean>> {
                when (it) {

                    is Result.Success<Boolean> -> {
                        it.data?.let {
                            dismissProgressDialog()
                            if (it) {
                                displaySnackBar(getString(R.string.contact_added_successfully))
                                initializeHandler()
                            }
                        }
                    }
                    is Result.Error -> {
                        dismissProgressDialog()
                        displaySnackBar(it.message)
                    }
                }
            })
    }

    private fun displaySnackBar(message: String) {
        Snackbar.make(
            add_contacts_parent_container,
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun initializeHandler() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            finish()
        }, DELAY_TIME)
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            job.cancel()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}