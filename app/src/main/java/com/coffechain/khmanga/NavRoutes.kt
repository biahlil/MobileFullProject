package com.coffechain.khmanga

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavRoutes(val route: String, val title: String? = null, val icon: ImageVector? = null) {
    data object Auth : NavRoutes("auth_screen")
    data object Main : NavRoutes("main_screen")
    data object CafeDetail : NavRoutes("cafe_detail/{cafeId}") {
        fun createRoute(cafeId: String) = "cafe_detail/$cafeId"
    }
    data object Home : NavRoutes("home_screen", "Home", Icons.Default.Home)
    data object Search : NavRoutes("search_screen", "Search", Icons.Default.Search)
    data object Transactions : NavRoutes("transactions_screen", "Transactions", Icons.Default.Receipt)
    data object Profile : NavRoutes("profile_screen", "Profile", Icons.Default.Person)
    data class BottomNavItem(
        val route: String,
        val label: String,
        val icon: ImageVector
    )


}