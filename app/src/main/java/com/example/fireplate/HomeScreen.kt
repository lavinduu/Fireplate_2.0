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
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip



@Composable
fun HomeScreen(navController: NavController) {
    val categories = listOf("Burger", "Pizza", "Drinks", "Fries", "Dessert")
    var selectedCategory by remember { mutableStateOf(categories.first()) }

    val foodItems = listOf(
        FoodItem("Cheese Burger", R.drawable.chicken, 4.5, 8.99),
        FoodItem("Chicken Burger", R.drawable.chicken, 4.3, 7.49),
        FoodItem("Veggie Burger", R.drawable.chicken, 4.0, 6.99)
    )

    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxSize()
        ) {
            // Title
            Text(
                text = "Find And Order Burger For You",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Search Bar
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = {
                    Row {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Find Your Food")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.LightGray.copy(alpha = 0.2f),
                    focusedContainerColor = Color.White
                ),
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Food Categories Tabs
            LazyRow {
                items(categories) { category ->
                    val isSelected = category == selectedCategory
                    Text(
                        text = category,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) Color(0xFFE91E63) else Color.LightGray)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickable { selectedCategory = category },
                        color = if (isSelected) Color.White else Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // "Most Popular" Title
            Text(
                text = "Most Popular",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Scrollable Food Cards
            LazyRow {
                items(foodItems) { item ->
                    FoodCard(item = item) {
                        navController.navigate("foodDetail")
                    }
                }
            }

        }
    }
}

@Composable
fun FoodCard(item: FoodItem, onClick: () -> Unit = {}) {
    var isFavourite by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .width(200.dp)
            .padding(end = 16.dp)
            .clickable { onClick() }, // ✅ Makes the entire card clickable
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = { isFavourite = !isFavourite },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favourite",
                        tint = if (isFavourite) Color.Red else Color.LightGray
                    )
                }
            }

            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.name,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Rating section
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFC107))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = item.rating.toString())
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "$${item.price}",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFFE91E63),
                fontWeight = FontWeight.Bold
            )
        }
    }
}


data class FoodItem(
    val name: String,
    val imageRes: Int,
    val rating: Double,
    val price: Double)
