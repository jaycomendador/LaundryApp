package com.washday.laundry.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.washday.laundry.model.LaundryOrder
import com.washday.laundry.ui.components.DigitalReceiptDialog
import com.washday.laundry.ui.theme.LaundryGreen
import com.washday.laundry.ui.theme.LaundryGreenLight
import com.washday.laundry.ui.theme.LaundryMateCanvas
import com.washday.laundry.ui.theme.LaundryMateLine
import com.washday.laundry.ui.theme.LaundryPinkPrimary
import com.washday.laundry.ui.theme.LaundryTextMuted
import com.washday.laundry.ui.theme.LaundryTextPrimary

@Composable
fun CompletedOrdersHistoryScreen(
    orders: List<LaundryOrder>,
    onShowToast: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedOrderForReceipt by remember { mutableStateOf<LaundryOrder?>(null) }

    val completedOrders = remember(orders, searchQuery) {
        orders.filter { order ->
            (order.status == "Claimed" || order.status == "Completed") &&
                    (searchQuery.isBlank() ||
                            order.customerName.contains(searchQuery, ignoreCase = true) ||
                            order.contactNumber.contains(searchQuery, ignoreCase = true) ||
                            order.id.contains(searchQuery, ignoreCase = true))
        }
    }

    val totalCompletedRevenue = remember(completedOrders) {
        completedOrders.sumOf { it.totalAmount }
    }

    Column(
        modifier = modifier
            .background(LaundryMateCanvas)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column {
            Text("Completed Order History", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = LaundryTextPrimary)
            Text("View past completed and claimed laundry records.", fontSize = 13.sp, color = LaundryTextMuted)
        }

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search by name, phone or order ID...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = LaundryPinkPrimary) },
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth()
        )

        // Summary Revenue Banner
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = LaundryGreenLight),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = LaundryGreen, modifier = Modifier.size(22.dp))
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "COMPLETED RECORDS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = LaundryGreen,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "${completedOrders.size} Orders Claimed",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = LaundryTextPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "₱${totalCompletedRevenue.toInt()}.00",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = LaundryGreen
                )
            }
        }

        // Completed Orders List
        if (completedOrders.isEmpty()) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.History, contentDescription = null, tint = LaundryTextMuted, modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("No Completed Orders Found", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = LaundryTextPrimary)
                    Text("Completed or claimed laundry orders will appear here.", fontSize = 12.sp, color = LaundryTextMuted)
                }
            }
        } else {
            completedOrders.forEach { order ->
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("Order #${order.id}", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = LaundryTextPrimary)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(LaundryGreenLight)
                                            .padding(horizontal = 8.dp, vertical = 2.dp)
                                    ) {
                                        Text(order.status, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = LaundryGreen)
                                    }
                                }
                                Text("${order.customerName} • ${order.contactNumber}", fontSize = 13.sp, color = LaundryTextMuted)
                            }

                            Text("₱${order.totalAmount.toInt()}.00", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = LaundryPinkPrimary)
                        }

                        HorizontalDivider(color = LaundryMateLine)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Service: ${order.serviceName} • ${order.weightKg} kg", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = LaundryTextPrimary)
                                Text("Date: ${order.date} ${order.time}", fontSize = 11.sp, color = LaundryTextMuted)
                            }

                            IconButton(
                                onClick = { selectedOrderForReceipt = order },
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(LaundryMateCanvas)
                            ) {
                                Icon(Icons.AutoMirrored.Filled.ReceiptLong, contentDescription = "Receipt", tint = LaundryTextPrimary, modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                }
            }
        }

        // Bottom spacing so content is never cut off by the navbar
        Spacer(modifier = Modifier.height(100.dp))
    }

    // Receipt Modal
    DigitalReceiptDialog(
        order = selectedOrderForReceipt,
        onDismiss = { selectedOrderForReceipt = null },
        onPrint = {
            onShowToast("Printing receipt for Order #${selectedOrderForReceipt?.id}")
            selectedOrderForReceipt = null
        }
    )
}
