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

@Composable
fun CartScreen(navController: NavController) {
    val cartItems = remember {
        mutableStateListOf(
            CartItem("Beef burger", R.drawable.chicken, 9.90f, true, 1),
            CartItem("Mini cheese burger", R.drawable.chicken, 5.00f, true, 1)
        )
    }

    val subTotal = cartItems.filter { it.selected }.fastSumBy { (it.price * it.quantity).toInt() }
    val shipping = 3.00f
    val total = subTotal + shipping

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Cart", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
        }

        Spacer(modifier = Modifier.height(16.dp))

        cartItems.forEach { item ->
            CartItemCard(item)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            SummaryRow("Sub total", "$${"%.2f".format(subTotal)}")
            SummaryRow("Shipping", "$${"%.2f".format(shipping)}")
            SummaryRow("Discount", "-")
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            SummaryRow("Total", "$${"%.2f".format(total)}", highlight = true)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "$ ${"%.2f".format(total)}",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { /* Handle pay now */ },
                modifier = Modifier
                    .height(50.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Text("Pay now")
            }
        }
    }
}

@Composable
fun CartItemCard(item: CartItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Checkbox(
                checked = item.selected,
                onCheckedChange = { item.selected = it },
                colors = CheckboxDefaults.colors(checkedColor = Color(0xFFFF9800))
            )

            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.name,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.name, fontWeight = FontWeight.Bold)
                Text(text = "$${"%.2f".format(item.price)}")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { if (item.quantity > 1) item.quantity-- }) {
                    Icon(Icons.Default.Remove, contentDescription = "Decrease")
                }
                Text(
                    text = item.quantity.toString(),
                    modifier = Modifier.width(24.dp),
                    textAlign = TextAlign.Center
                )
                IconButton(onClick = { item.quantity++ }) {
                    Icon(Icons.Default.Add, contentDescription = "Increase")
                }
            }
        }
    }
}

@Composable
fun SummaryRow(label: String, value: String, highlight: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label)
        Text(
            value,
            fontWeight = if (highlight) FontWeight.Bold else FontWeight.Normal,
            color = if (highlight) Color(0xFFFF9800) else Color.Black
        )
    }
}

data class CartItem(
    val name: String,
    val imageRes: Int,
    val price: Float,
    var selected: Boolean,
    var quantity: Int
)
