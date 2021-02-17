package com.task.maxab.presentation

import android.content.Intent
import android.net.Uri
import com.task.maxab.presentation.contactsStory.BaseContactsActivity

const val VALID_PHONE_NUMBER_LENGTH = 11


fun String.isValidPhone(): Boolean {
    if (!this.isEmpty() && this.length >= VALID_PHONE_NUMBER_LENGTH && android.util.Patterns.PHONE.matcher(
            this
        ).matches()
    ) {
        return true
    }
    return false
}


fun <T> List<T>.toGenericArrayList(): ArrayList<T> {
    return ArrayList(this)
}