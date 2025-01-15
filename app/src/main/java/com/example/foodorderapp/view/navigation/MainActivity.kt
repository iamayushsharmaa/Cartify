package com.example.foodorderapp.view.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.foodorderapp.ui.theme.FoodOrderAppTheme
import com.example.foodorderapp.viewmodel.CartViewModel
import com.example.foodorderapp.viewmodel.FirebaseViewModel
import com.example.foodorderapp.viewmodel.FirestoreViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodOrderAppTheme {
                auth = FirebaseAuth.getInstance()
                val firebaseViewModel = hiltViewModel<FirebaseViewModel>()
                val firestoreViewModel = hiltViewModel<FirestoreViewModel>()
                val cartViewModel = hiltViewModel<CartViewModel>()
                val isUserLogin = auth.currentUser != null
                NavigationScreen(firebaseViewModel, isUserLogin,firestoreViewModel,cartViewModel)
            }
        }
    }
}
