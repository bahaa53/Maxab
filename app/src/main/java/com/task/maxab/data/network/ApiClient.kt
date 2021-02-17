package com.task.maxab.data.network

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


val firebaseDBLazy by lazy {
    Firebase.firestore
}

