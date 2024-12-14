package com.example.foodorderapp.repository.firebase

import android.app.Activity
import com.example.foodorderapp.data.ResultState
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow


interface FirebaseRepository {
    suspend fun createUserWithPhone(phoneNumber: String, activity: Activity): Flow<ResultState<String>>
    suspend fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential,firestore : FirebaseFirestore,phoneNumber: String): Flow<ResultState<String>>
    val verificationId: String?
}