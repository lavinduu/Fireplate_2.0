package com.example.fireplate

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun FirePlateApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "landing") {
        composable("landing") { LandingScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("SignUp") { SignUpScreen(navController) }
        // Screens with bottom bar
        composable("home") { HomeScreen(navController) }
        composable("cart") { CartScreen(navController) }
        composable("favourites") { FavouritesScreen(navController) }
        composable("notifications") { NotificationsScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("foodDetail") { FoodDetailScreen(navController) }


    }
}