@file:Suppress("UNREACHABLE_CODE")

package com.example.foodorderapp.view.main.bottomnav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.Navigation
import com.example.foodorderapp.ui.theme.poppinsFontFamily

@Composable
fun BottomNavigationBar(
    currentRoute: String,
    bottomNavItems: List<BottomNavItem>,
    onItemSelected: (BottomNavItem) -> Unit
) {
    NavigationBar (
        containerColor = Color.White,
        modifier = Modifier
            .height(66.dp)
            .padding(bottom = 8.dp)
            .background(Color.White)
    ){
        bottomNavItems.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.outlineIcon),
                        contentDescription = "",
                        modifier = Modifier.size(28.dp),
                        tint = if (isSelected) Color.Black else Color.Gray
                    )
                },
                selected = isSelected,
                onClick = { onItemSelected(item) },
                alwaysShowLabel = true,
                colors = NavigationBarItemColors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    unselectedIconColor = Color.Black,
                    unselectedTextColor = Color.Black,
                    selectedIndicatorColor = Color.White,
                    disabledIconColor = Color.DarkGray,
                    disabledTextColor = Color.DarkGray
                )
            )
        }
    }
}