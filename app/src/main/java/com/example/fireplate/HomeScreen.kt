package com.example.fireplate

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.graphics.vector.ImageVector


@Composable
fun HomeScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf("Pizza") }
    val categories = listOf("Pizza", "Burger", "Drinks", "Dessert", "Pasta")
    val popularItems = listOf(
        FoodItem("Cheese Pizza", R.drawable.chicken, 4.5f, "$12.99"),
        FoodItem("Double Burger", R.drawable.chicken, 4.2f, "$9.49"),
        FoodItem("Cold Drink", R.drawable.chicken, 4.0f, "$2.99")
    )

    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Find and order food for You...!",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = {
                    Row {
                        Icon(Icons.Default.Search, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Find your food")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                categories.forEach { category ->
                    val isSelected = selectedTab == category
                    Text(
                        text = category,
                        color = if (isSelected) Color.White else Color.Black,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable { selectedTab = category }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Most Popular", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                popularItems.forEach { item ->
                    FoodCard(item)
                }
            }
        }
    }
}

@Composable
fun FoodCard(item: FoodItem) {
    var isFavourite by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(200.dp)
            .padding(end = 12.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = { isFavourite = !isFavourite },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = if (isFavourite) Color.Red else Color.Gray
                )
            }
        }
        Image(
            painter = painterResource(id = item.imageRes),
            contentDescription = item.name,
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
        )
        Column(modifier = Modifier.padding(8.dp)) {
            Text(item.name, fontWeight = FontWeight.Bold)
            RatingBar(rating = item.rating)
            Text(item.price, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun RatingBar(rating: Float) {
    Row {
        repeat(5) { i ->
            val icon = if (i < rating.toInt()) Icons.Default.Star else Icons.Default.StarBorder
            Icon(icon, contentDescription = null, tint = Color(0xFFFFC107))
        }
    }
}

@Composable
fun BottomNavBar(navController: NavController) {
    var selectedIndex by remember { mutableStateOf(0) }
    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home, "home"),
        BottomNavItem("Cart", Icons.Default.ShoppingCart, "cart"),
        BottomNavItem("Favourites", Icons.Default.Favorite, "favourites"),
        BottomNavItem("Profile", Icons.Default.Person, "profile")
    )

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                    navController.navigate(item.route)
                }
            )
        }
    }
}


data class FoodItem(val name: String, val imageRes: Int, val rating: Float, val price: String)
data class BottomNavItem(val label: String, val icon: ImageVector, val route: String)

