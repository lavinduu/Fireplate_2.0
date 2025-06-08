package com.example.fireplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fireplate.ui.theme.FirePlateTheme
import androidx.compose.material3.*
import androidx.navigation.NavController


@Composable
fun ProfileScreen(navController: NavController) {
    // UI design here
    Button(onClick = { navController.navigate("login") }) {
        Text("Log Out")
    }
}