package com.example.fireplate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun FavouritesScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        // Content inside HomeScreen
        Column(modifier = Modifier.padding(paddingValues)) {
            Text("Welcome to Favourites Screen")
            // Add cards, lists, etc.
        }
    }
}