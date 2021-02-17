package com.task.maxab.presentation.contactsStory

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.task.maxab.presentation.CustomProgressBar
import com.task.maxab.presentation.MyApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import kotlin.coroutines.CoroutineContext

open class BaseContactsActivity : AppCompatActivity(), KodeinAware, CoroutineScope {

    override val kodein: Kodein by closestKodein(MyApp.applicationContext())
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    protected lateinit var job: Job

    private val progressDialog: CustomProgressBar = CustomProgressBar()

    /////////////////Declaring View Model //////////////////////////////////////////
    protected val contactsViewModelFactory: ContactsViewModelFactory by instance()
    protected lateinit var contactsViewModel: ContactsViewModel

    protected fun setupViewModel() {
        contactsViewModel = ViewModelProvider(this, contactsViewModelFactory)
            .get(ContactsViewModel::class.java)
    }

    protected fun showProgressDialog() {
        progressDialog.show(this)
    }

    protected fun dismissProgressDialog() {
        progressDialog.dismiss()
    }
}