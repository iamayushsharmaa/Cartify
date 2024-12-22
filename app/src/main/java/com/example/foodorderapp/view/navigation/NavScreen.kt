package com.example.foodorderapp.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.foodorderapp.view.login.Login
import com.example.foodorderapp.view.main.CategoryScreen
import com.example.foodorderapp.view.main.HomeScreen
import com.example.foodorderapp.view.main.SearchScreen
import com.example.foodorderapp.viewmodel.FirebaseViewModel
import com.example.foodorderapp.viewmodel.FirestoreViewModel
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun NavigationScreen(
    firebaseViewModel: FirebaseViewModel,
    isUserLogin: Boolean,
    firestoreViewModel: FirestoreViewModel
) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = if (isUserLogin) "homeScreen" else "login"
    ){
        composable("login") {
            Login(
                navController,
            )
        }
        composable("homeScreen") {
            HomeScreen(navController)
        }
        composable("searchScreen") {
            SearchScreen(firestoreViewModel)
        }
        composable(
            "categoryScreen/{category}",
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category").orEmpty()
            CategoryScreen(
                navController = navController,
                category = category,
                firestoreViewModel = firestoreViewModel,
                //cartViewModel = TODO()
            )
        }
    }
}