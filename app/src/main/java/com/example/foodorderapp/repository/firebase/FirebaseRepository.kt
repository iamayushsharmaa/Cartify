package com.example.foodorderapp.repository.firebase

import android.app.Activity
import com.example.foodorderapp.data.model.ResultState
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow


interface FirebaseRepository {
    suspend fun registerUser(email: String, password: String): AuthResult
    suspend fun loginUser(email: String, password: String): AuthResult
}