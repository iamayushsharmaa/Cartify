package com.example.foodorderapp.view.main.bottomnav

import com.example.foodorderapp.R

sealed class BottomNavItem(
    val route: String,
    val outlineIcon: Int,
) {
    object Home : BottomNavItem("home", R.drawable.house)
    object Search : BottomNavItem("search", R.drawable.search)
    object Cart : BottomNavItem("cart",  R.drawable.cartt)
    object Profile : BottomNavItem("profile", R.drawable.user_profile)
}