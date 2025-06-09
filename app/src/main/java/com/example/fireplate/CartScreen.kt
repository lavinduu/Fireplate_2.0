package com.example.fireplate

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastSumBy
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp



@Composable
fun CartScreen(navController: NavController) {
    val cartItems = remember {
        mutableStateListOf(
            CartItem("Hot Coffee", "Cappuccino", 5.99, R.drawable.chicken),
            CartItem("Cold Coffee", "Iced Latte", 6.49, R.drawable.chicken)
        )
    }

    val selectedItems = remember { mutableStateListOf<Boolean>().apply { addAll(List(cartItems.size) { false }) } }

    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.White)
        ) {
            // Top Bar with Back Button and Title
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Cart",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(48.dp)) // To balance the back button space
            }

            // Cart Item Cards
            LazyColumn(modifier = Modifier.weight(1f)) {
                itemsIndexed(cartItems) { index, item ->
                    CartItemCard(
                        item = item,
                        isChecked = selectedItems[index],
                        onCheckedChange = { checked ->
                            selectedItems[index] = checked
                        }
                    )
                }
            }

            // Summary Section
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
            ) {
                SummaryRow("Sub Total", "$${String.format("%.2f", cartItems.sumOf { it.price })}")
                SummaryRow("Shipping", "$2.50")
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                SummaryRow("Total", "$${String.format("%.2f", cartItems.sumOf { it.price } + 2.5) }", isTotal = true)

                Spacer(modifier = Modifier.height(16.dp))

                // Pay Now Button
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = { /* handle payment */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Pay Now", color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

// ----------------------------
// Components
// ----------------------------

@Composable
fun CartItemCard(item: CartItem, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFFFF9800), // Orange
                    uncheckedColor = Color.LightGray
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(item.category, style = MaterialTheme.typography.bodySmall)
                Text(item.name, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                Text("$${String.format("%.2f", item.price)}", style = MaterialTheme.typography.bodyMedium)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    IconButton(onClick = { /* decrease qty */ }) {
                        Icon(Icons.Default.Remove, contentDescription = "Decrease")
                    }
                    Text("1", style = MaterialTheme.typography.bodyMedium)
                    IconButton(onClick = { /* increase qty */ }) {
                        Icon(Icons.Default.Add, contentDescription = "Increase")
                    }
                }
            }
        }
    }
}

@Composable
fun SummaryRow(label: String, price: String, isTotal: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        Text(
            price,
            style = if (isTotal) MaterialTheme.typography.bodyLarge.copy(color = Color(0xFFFF9800), fontWeight = FontWeight.Bold)
            else MaterialTheme.typography.bodyLarge
        )
    }
}

// ----------------------------
// Data Class
// ----------------------------
data class CartItem(
    val category: String,
    val name: String,
    val price: Double,
    val imageRes: Int
)
