package com.coffechain.khmanga.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.coffechain.khmanga.NavRoutes
import com.coffechain.khmanga.NavRoutes.BottomNavItem
import com.coffechain.khmanga.ui.cafeselect.CafeSelectScreen
import com.coffechain.khmanga.ui.cafeselect.CafeSelectViewModel
import com.coffechain.khmanga.ui.components.CafeBottomNavBar
import com.coffechain.khmanga.ui.components.CafeTopBar
import com.coffechain.khmanga.ui.detail.CafeDetailScreen
import com.coffechain.khmanga.ui.profile.ProfileScreen
import com.coffechain.khmanga.ui.search.SearchScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {
    val innerNavController = rememberNavController()
    val navBackStackEntry by innerNavController.currentBackStackEntryAsState()
    val userProfileState by viewModel.userProfileState.collectAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavItems = listOf(
        BottomNavItem(
            route = NavRoutes.Home.route,
            label = "Home",
            icon = Icons.Default.Home
        ),
        BottomNavItem(
            route = NavRoutes.Search.route,
            label = "Search",
            icon = Icons.Default.Search
        ),
        BottomNavItem(
            route = NavRoutes.Transactions.route,
            label = "Transaksi",
            icon = Icons.AutoMirrored.Filled.List
        ),
        BottomNavItem(
            route = NavRoutes.Profile.route,
            label = "Profil",
            icon = Icons.Default.Person
        )
    )

    Scaffold(
        topBar = {
            if (currentRoute != NavRoutes.Search.route) {
                CafeTopBar(
                    profileUrl = if (userProfileState is UserProfileState.Success) {
                        (userProfileState as UserProfileState.Success).photoUrl
                    } else null,
                    profileOnClicked = {
                        innerNavController.navigate("profile_screen")
                    },
                    searchOnClicked = {
                        innerNavController.navigate(NavRoutes.Search.route)
                    },
                    backOnClick = {innerNavController.popBackStack()}
                )
            }
                 },
        bottomBar = {
            CafeBottomNavBar(
                items = bottomNavItems,
                selectedIndex = bottomNavItems.indexOfFirst { it.route == currentRoute }.coerceAtLeast(0),
                onItemSelected = { index: Int ->
                    innerNavController.navigate(bottomNavItems[index].route) {
                        popUpTo(innerNavController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = innerNavController,
            startDestination = NavRoutes.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavRoutes.Home.route) {
                val cafeViewModel: CafeSelectViewModel = hiltViewModel()

                val uiState by cafeViewModel.uiState.collectAsState()
                CafeSelectScreen(
                    uiState = uiState,
                    onRefresh = { innerNavController.navigate(NavRoutes.Home.route) },
                    onItemClick = { cafeId ->
                        innerNavController.navigate(NavRoutes.CafeDetail.createRoute(cafeId = cafeId))
                    },
                    onBookmarkClick = { /*cafeViewModel.toggleBookmark(it)*/ }
                )
            }
            composable(NavRoutes.Transactions.route) {
                 Text("Transaction Screen")
            }
            composable(NavRoutes.Search.route) {
                SearchScreen()
            }
            composable(route = NavRoutes.Profile.route) {
                ProfileScreen(
                    onNavigateToAuth = onLogout
                )
            }
            composable(
                route = NavRoutes.CafeDetail.route,
                arguments = listOf(navArgument("cafeId") { type = NavType.StringType })
            ) {
                CafeDetailScreen(
                )
            }

        }
    }
}