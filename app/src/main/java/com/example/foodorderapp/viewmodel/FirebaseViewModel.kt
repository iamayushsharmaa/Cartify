package com.example.foodorderapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodorderapp.data.auth.User
import com.example.foodorderapp.data.model.AuthState
import com.example.foodorderapp.repository.firebase.AuthFirestoreRepository
import com.example.foodorderapp.repository.firebase.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FirebaseViewModel @Inject constructor(
    private val repository: FirebaseRepository,
    private val firestoreDb: AuthFirestoreRepository
) : ViewModel(){

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun registerUser(email: String, password: String, name: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val authResult = repository.registerUser(email, password)
                val user = User(email = email, name = name)
                firestoreDb.saveUser(user)
                _authState.value = AuthState.Success("User registered successfully")
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                repository.loginUser(email, password)
                _authState.value = AuthState.Success("User logged in successfully")
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Unknown error")
            }
        }
    }


}