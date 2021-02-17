package com.task.maxab.presentation

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.task.maxab.R

class CustomProgressBar {

    private  var dialog: Dialog? = null


    fun show(context: Context): Dialog? {
        val inflator =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflator.inflate(R.layout.custom_progress_layout, null)
        dialog = Dialog(context, R.style.DialogCustomTheme)
        dialog?.setContentView(view)
        dialog?.setCancelable(false)
        dialog?.show()
        return dialog
    }

    fun dismiss() {
        dialog?.dismiss()
    }
}