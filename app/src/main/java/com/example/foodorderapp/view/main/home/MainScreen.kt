package com.example.foodorderapp.view.main.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.foodorderapp.view.main.bottomnav.BottomNavItem
import com.example.foodorderapp.view.main.bottomnav.BottomNavigationBar
import com.example.foodorderapp.view.main.bottomnav.Cart
import com.example.foodorderapp.view.main.bottomnav.CategoryScreen
import com.example.foodorderapp.view.main.bottomnav.Home
import com.example.foodorderapp.view.main.bottomnav.Profile
import com.example.foodorderapp.view.main.bottomnav.Search
import com.example.foodorderapp.viewmodel.CartViewModel
import com.example.foodorderapp.viewmodel.FirebaseViewModel
import com.example.foodorderapp.viewmodel.FirestoreViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    firestoreViewModel: FirestoreViewModel,
    firebaseViewModel: FirebaseViewModel,
    cartViewModel: CartViewModel
) {
    val navController = rememberNavController()

    val bottomNavItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Cart,
        BottomNavItem.Profile
    )
    var currentRoute by remember { mutableStateOf(BottomNavItem.Home.route) }
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            currentRoute = backStackEntry.destination.route ?: BottomNavItem.Home.route
        }
    }
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                bottomNavItems,
                onItemSelected = { navItem ->
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = currentRoute,
            modifier = Modifier.padding(innerPadding),
        ){
            composable(BottomNavItem.Home.route) {
                Home(
                    navController,
                    onSearchClick = {
                        navController.navigate(BottomNavItem.Search.route)
                    }
            )}
            composable(BottomNavItem.Search.route) {
                Search(
                    firestoreViewModel = firestoreViewModel,
                    cartViewModel = cartViewModel
                )
            }
            composable(BottomNavItem.Cart.route) { Cart(navController) }
            composable(BottomNavItem.Profile.route) { Profile(navController) }

            composable(
                "categoryScreen/{category}",
                arguments = listOf(navArgument("category") { type = NavType.StringType })
            ) { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category").orEmpty()
                CategoryScreen(
                    navController = navController,
                    category = category,
                    firestoreViewModel = firestoreViewModel,
                )
            }
        }
    }
}