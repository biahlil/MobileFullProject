package com.coffechain.khmanga

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coffechain.khmanga.ui.auth.AuthScreen
import com.coffechain.khmanga.ui.main.MainScreen
import com.google.firebase.auth.FirebaseAuth


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val startDestination = if (FirebaseAuth.getInstance().currentUser != null) {
        NavRoutes.Main.route
    } else {
        NavRoutes.Auth.route
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(NavRoutes.Auth.route) {
            AuthScreen(
                onSignInSuccess = {
                    navController.navigate(NavRoutes.Main.route) {
                        popUpTo(NavRoutes.Auth.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(NavRoutes.Main.route) {
            MainScreen(
                onLogout = {
                    // Perintahkan NavController utama untuk kembali ke layar Auth
                    navController.navigate(NavRoutes.Auth.route) {
                        // Hapus semua layar dari back stack agar tidak bisa kembali
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }

            )
        }


    }
}