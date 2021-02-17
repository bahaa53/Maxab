package com.task.maxab.presentation

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import com.tbruyelle.rxpermissions2.RxPermissions


fun askForPhoneCallPermission(activity: AppCompatActivity, listener: (Boolean) -> Unit) {
    val rxPermissions = RxPermissions(activity)
    rxPermissions.request(
        Manifest.permission.CALL_PHONE
    )
        .subscribe { granted ->
            if (granted) { // Always true pre-M
                listener(true)
            } else {
                listener(false)
            }
        }
}