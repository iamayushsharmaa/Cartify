package com.example.foodorderapp.view.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.foodorderapp.ui.theme.FoodOrderAppTheme
import com.example.foodorderapp.viewmodel.FirebaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var auth : FirebaseAuth
    val firestore= Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodOrderAppTheme {
                auth = FirebaseAuth.getInstance()
                val firebaseViewModel: FirebaseViewModel by viewModels()
               // val firestoreViewModel = hilt
                val isUserLogin = auth.currentUser != null
                NavigationScreen(firebaseViewModel, isUserLogin, firestore)
            }
        }
    }
}
