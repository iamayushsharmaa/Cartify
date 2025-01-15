package com.example.foodorderapp.view.main.bottomnav

import com.example.foodorderapp.R

sealed class BottomNavItem(
    val route: String,
    val outlineIcon: Int,
    val title: String
) {
    object Home : BottomNavItem("home", R.drawable.house, "Home")
    object Search : BottomNavItem("search", R.drawable.search, "Search")
    object Cart : BottomNavItem("cart",  R.drawable.cartt, "Bookmark")
    object Profile : BottomNavItem("profile", R.drawable.user_profile, "Profile")
}