package com.task.maxab.presentation.contactsStory

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.task.maxab.R
import com.task.maxab.domain.entities.Contact
import com.task.maxab.domain.repositories.Result
import com.task.maxab.presentation.createTextChangeObservable
import com.task.maxab.presentation.toGenericArrayList
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_contacts_layout.*
import kotlinx.android.synthetic.main.custom_toolbar_layout.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance
import java.lang.Exception


const val INTENT_CONTACTS_LIST_PARAM = "contacts_list"
const val INTENT_SELECTED_CONTANT_KEY = "selected_contact"

class ContactsActivity : BaseContactsActivity() {

    ///////////////////Declaring Layout Manager & Recycler Adapter ////////////////////////
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var contactsAdapter: ContactsAdapter
    private lateinit var contacts: List<Contact>
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_contacts_layout)
        job = Job()
        setupViewModel()
        setupRecyclerView()
        handleActions()
        handleFilterContacts()
    }

    override fun onResume() {
        super.onResume()
        getAllContacts()
    }

    private fun handleActions() {
        iv_search.setOnClickListener {
            if (et_search.visibility == View.VISIBLE) {
                et_search.visibility = View.GONE
            } else {
                et_search.visibility = View.VISIBLE
            }
        }

        fab.setOnClickListener {
            moveToAddContactsActivity()
        }
    }

    private fun moveToAddContactsActivity() {
        val addContactsIntent = Intent(this, AddContactsActivity::class.java)
        addContactsIntent.putExtra(
            INTENT_CONTACTS_LIST_PARAM,
            contacts.toGenericArrayList()
        )
        startActivity(addContactsIntent)
    }

    private fun handleFilterContacts() {
        val initialSearchDisposable = et_search.createTextChangeObservable() {
            if (!it.isEmpty()) {
                filterContacts(contacts, it)
            } else {
                contactsAdapter.setContactsData(contacts)
            }
        }.subscribe()
        compositeDisposable.add(initialSearchDisposable)
    }

    private fun setupRecyclerView() {
        contactsAdapter =
            ContactsAdapter() {
                openContactDetailActivity(it)
            }
        linearLayoutManager =
            LinearLayoutManager(applicationContext)
        rv_contacts.apply {
            layoutManager = linearLayoutManager
            adapter = contactsAdapter
        }
    }

    private fun openContactDetailActivity(contact: Contact) {
        val contactDetailIntent = Intent(this, ContactDetailActivity::class.java)
        contactDetailIntent.putExtra(INTENT_SELECTED_CONTANT_KEY, contact)
        startActivity(contactDetailIntent)
    }

    private fun getAllContacts() = launch {
        showProgressDialog()
        val contactsResponse = contactsViewModel.getAllContacts()
        observeForContactsData(contactsResponse)
    }

    private fun filterContacts(contacts: List<Contact>, it: String) {
        val filteredContacts = contactsViewModel.filterContacts(contacts, it)
        contactsAdapter.setContactsData(filteredContacts)
    }

    private fun observeForContactsData(forgetPasswordResponse: LiveData<com.task.maxab.domain.repositories.Result<List<Contact>>>) {
        forgetPasswordResponse.observe(
            this@ContactsActivity,
            Observer<Result<List<Contact>>> {
                when (it) {
                    is Result.Success<List<Contact>> -> {
                        dismissProgressDialog()
                        it.data?.let {
                            this.contacts = it
                            contactsAdapter.setContactsData(it)
                        }
                    }
                    is Result.Error -> {
                        dismissProgressDialog()
                        this.contacts = listOf()
                        Snackbar.make(parent_container, it.message, Snackbar.LENGTH_LONG).show()
                    }
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            job.cancel()
            compositeDisposable.clear()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}