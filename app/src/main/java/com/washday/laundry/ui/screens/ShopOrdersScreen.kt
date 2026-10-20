package com.washday.laundry.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import com.washday.laundry.ui.theme.WashdayAmber
import com.washday.laundry.ui.theme.WashdayAmberLight
import com.washday.laundry.ui.theme.WashdayBlue
import com.washday.laundry.ui.theme.WashdayBlueLight
import com.washday.laundry.ui.theme.WashdayCanvas
import com.washday.laundry.ui.theme.WashdayGreen
import com.washday.laundry.ui.theme.WashdayGreenLight
import com.washday.laundry.ui.theme.WashdayLine
import com.washday.laundry.ui.theme.WashdayTextMuted
import com.washday.laundry.ui.theme.WashdayTextPrimary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ShopOrdersScreen(
    orders: List<LaundryOrder>,
    onUpdateOrderStatus: (String, String) -> Unit,
    onShowToast: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf("All") }
    var selectedOrderForReceipt by remember { mutableStateOf<LaundryOrder?>(null) }

    // Active orders exclude Completed and Claimed orders
    val activeOrders = remember(orders) {
        orders.filter { it.status != "Completed" && it.status != "Claimed" }
    }

    val filterOptions = listOf("All", "Pending", "Received", "Washing", "Drying", "Ready for Pickup")
    val statusOptions = listOf("Pending", "Received", "Washing", "Drying", "Ready for Pickup", "Completed", "Claimed")

    val filteredOrders = if (selectedFilter == "All") activeOrders else activeOrders.filter { it.status == selectedFilter }

    Column(
        modifier = modifier
            .background(WashdayCanvas)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column {
            Text("Active Customer Orders", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = WashdayTextPrimary)
            Text("View in-progress orders. Completed & claimed orders automatically move to History.", fontSize = 13.sp, color = WashdayTextMuted)
        }

        // Status Filter Pills
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            filterOptions.forEach { filter ->
                val isSelected = filter == selectedFilter
                val bg = if (isSelected) WashdayBlue else Color.White
                val fg = if (isSelected) Color.White else WashdayTextMuted

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(bg)
                        .clickable { selectedFilter = filter }
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = filter,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = fg
                    )
                }
            }
        }

        if (filteredOrders.isEmpty()) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.AutoMirrored.Filled.Assignment, contentDescription = null, tint = WashdayTextMuted, modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("No Active Orders", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = WashdayTextPrimary)
                    Text("All orders have been completed and moved to History.", fontSize = 12.sp, color = WashdayTextMuted)
                }
            }
        } else {
            filteredOrders.forEach { order ->
                var showStatusMenu by remember { mutableStateOf(false) }

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("Order #${order.id}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = WashdayTextPrimary)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(order.customerName, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = WashdayBlue)
                                }
                                Text("${order.contactNumber} • ${order.date}", fontSize = 12.sp, color = WashdayTextMuted)
                            }

                            Text("₱${order.totalAmount.toInt()}.00", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = WashdayTextPrimary)
                        }

                        HorizontalDivider(color = WashdayLine)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("${order.serviceName} • ${order.weightKg} kg", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = WashdayTextPrimary)
                                Text("Payment: ${order.paymentStatus}", fontSize = 11.sp, color = WashdayGreen, fontWeight = FontWeight.Bold)
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // Status Dropdown
                                Box {
                                    val statusBg = when (order.status) {
                                        "Completed", "Claimed", "Ready for Pickup" -> WashdayGreenLight
                                        "Washing", "Drying" -> WashdayBlueLight
                                        else -> WashdayAmberLight
                                    }
                                    val statusFg = when (order.status) {
                                        "Completed", "Claimed", "Ready for Pickup" -> WashdayGreen
                                        "Washing", "Drying" -> WashdayBlue
                                        else -> WashdayAmber
                                    }

                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(statusBg)
                                            .clickable { showStatusMenu = true }
                                            .padding(horizontal = 12.dp, vertical = 6.dp)
                                    ) {
                                        Text(
                                            text = "${order.status} ▾",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = statusFg
                                        )
                                    }

                                    DropdownMenu(
                                        expanded = showStatusMenu,
                                        onDismissRequest = { showStatusMenu = false }
                                    ) {
                                        statusOptions.forEach { st ->
                                            DropdownMenuItem(
                                                text = { Text(st, fontSize = 13.sp) },
                                                onClick = {
                                                    onUpdateOrderStatus(order.id, st)
                                                    showStatusMenu = false
                                                    if (st == "Completed" || st == "Claimed") {
                                                        onShowToast("Order #${order.id} marked as $st and moved to History 📜")
                                                    } else {
                                                        onShowToast("Order #${order.id} status updated to $st")
                                                    }
                                                }
                                            )
                                        }
                                    }
                                }

                                // Receipt Button
                                OutlinedButton(
                                    onClick = {
                                        selectedOrderForReceipt = order
                                    },
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(Icons.AutoMirrored.Filled.ReceiptLong, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Receipt", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Bottom spacing so content is never cut off by the navbar
        Spacer(modifier = Modifier.height(100.dp))
    }

    // Digital Receipt Modal
    DigitalReceiptDialog(
        order = selectedOrderForReceipt,
        onDismiss = { selectedOrderForReceipt = null },
        onPrint = {
            onShowToast("Printing receipt for Order #${selectedOrderForReceipt?.id}")
            selectedOrderForReceipt = null
        }
    )
}
