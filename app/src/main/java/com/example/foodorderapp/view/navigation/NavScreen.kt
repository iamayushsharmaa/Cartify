package com.example.foodorderapp.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodorderapp.view.login.Login
import com.example.foodorderapp.view.main.home.MainScreen
import com.example.foodorderapp.viewmodel.CartViewModel
import com.example.foodorderapp.viewmodel.FirebaseViewModel
import com.example.foodorderapp.viewmodel.FirestoreViewModel


@Composable
fun NavigationScreen(
    firebaseViewModel: FirebaseViewModel,
    isUserLogin: Boolean,
    firestoreViewModel: FirestoreViewModel,
    cartViewModel: CartViewModel
) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = if (isUserLogin) "main_screen" else "login"
    ){
        composable("login") {
            Login(
                navController,
            )
        }
        composable("main_screen"){
            MainScreen(firestoreViewModel,firebaseViewModel, cartViewModel)
        }
    }
}