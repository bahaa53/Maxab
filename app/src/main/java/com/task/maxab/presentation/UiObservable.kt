package com.task.maxab.presentation

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import io.reactivex.Observable


fun EditText.createTextChangeObservable(listener: (String) -> Unit): Observable<String> {
    // 2
    val textChangeObservable = Observable.create<String> { emitter ->
        // 3
        val textWatcher = object : TextWatcher {

            override fun afterTextChanged(s: Editable?) = Unit

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) = Unit

            // 4
            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                listener(s?.toString()!!)
            }
        }

        // 5
        this.addTextChangedListener(textWatcher)

        // 6
        emitter.setCancellable {
            this.removeTextChangedListener(textWatcher)
        }
    }

    // 7
    return textChangeObservable
}

