package com.task.maxab.presentation.contactsStory

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.task.maxab.R
import com.task.maxab.domain.entities.Contact
import com.task.maxab.presentation.askForPhoneCallPermission
import kotlinx.android.synthetic.main.activity_contact_detail_layout.*

class ContactDetailActivity : BaseContactsActivity() {

    private lateinit var contact: Contact
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail_layout)
        supportActionBar?.hide()
        getExtraData()
        setDataToUi()
        handleClickActions()
    }

    private fun getExtraData() {
        contact = intent.getParcelableExtra<Contact>(INTENT_SELECTED_CONTANT_KEY) ?: Contact()
    }

    private fun handleClickActions() {
        iv_phone.setOnClickListener {
            contact.Phone?.callAction()
        }

        iv_back.setOnClickListener {
            finish()
        }
    }

    private fun setDataToUi() {
        tv_contact_name_detail.text = contact.Name ?: ""
        tv_contact_phone_number.text = contact.Phone ?: ""
    }

    private fun String.callAction() {
        askForPhoneCallPermission(activity = (this@ContactDetailActivity)) {
            if (it) {
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:${this}")
                startActivity(callIntent)
            }
        }
    }
}