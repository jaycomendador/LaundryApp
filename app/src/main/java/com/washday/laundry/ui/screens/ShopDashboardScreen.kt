package com.washday.laundry.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.washday.laundry.model.DailySalesSummary
import com.washday.laundry.model.LaundryOrder
import com.washday.laundry.model.ShopTab
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

@Composable
fun ShopDashboardScreen(
    orders: List<LaundryOrder>,
    dailySalesSummary: DailySalesSummary,
    onNavigateToTab: (ShopTab) -> Unit,
    onUpdateOrderStatus: (String, String) -> Unit,
    onShowToast: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedOrderForReceipt by remember { mutableStateOf<LaundryOrder?>(null) }
    val statusOptions = listOf("Received", "Washing", "Drying", "Ready for Pickup", "Completed")

    Column(
        modifier = modifier
            .background(WashdayCanvas)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Shop Manager Hero Banner
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = WashdayTextPrimary),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White.copy(alpha = 0.15f))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "LAUNDRY SHOP MANAGER",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                letterSpacing = 1.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Lena Morales",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Text(
                            text = "Your shop is running smoothly.",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(WashdayBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Storefront, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.1f))
                        .padding(14.dp)
                ) {
                    Text(
                        text = "3 orders need your attention, and 5 are ready for pickup.",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
            }
        }

        // Daily Performance Stat Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.weight(1f)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Today's Sales", fontSize = 11.sp, color = WashdayTextMuted, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("₱8,460", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = WashdayBlue)
                    Text("+12% vs yesterday", fontSize = 10.sp, color = WashdayGreen, fontWeight = FontWeight.Bold)
                }
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.weight(1f)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Attention Req.", fontSize = 11.sp, color = WashdayTextMuted, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("${dailySalesSummary.ordersNeedingAttention} orders", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = WashdayAmber)
                    Text("Action needed", fontSize = 10.sp, color = WashdayAmber, fontWeight = FontWeight.Bold)
                }
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.weight(1f)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Ready Pickup", fontSize = 11.sp, color = WashdayTextMuted, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("${dailySalesSummary.ordersReadyForPickup} orders", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = WashdayGreen)
                    Text("Ready for customer", fontSize = 10.sp, color = WashdayGreen, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Quick Action Shortcuts
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Quick Actions", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = WashdayTextPrimary)
                Text("Shortcuts for daily tasks", fontSize = 12.sp, color = WashdayTextMuted)

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { onNavigateToTab(ShopTab.NEW_ORDER) },
                        colors = ButtonDefaults.buttonColors(containerColor = WashdayBlue),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("New Order", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = { onNavigateToTab(ShopTab.SALES) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.BarChart, contentDescription = null, modifier = Modifier.size(16.dp), tint = WashdayTextPrimary)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Sales Report", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = WashdayTextPrimary)
                    }

                    OutlinedButton(
                        onClick = { onNavigateToTab(ShopTab.ORDERS) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.ListAlt, contentDescription = null, modifier = Modifier.size(16.dp), tint = WashdayTextPrimary)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("All Orders", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = WashdayTextPrimary)
                    }
                }
            }
        }

        // Recent Orders Table
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Recent Orders", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = WashdayTextPrimary)
                        Text("Track today's latest laundry orders", fontSize = 12.sp, color = WashdayTextMuted)
                    }
                }

                HorizontalDivider(color = WashdayLine)

                orders.take(4).forEach { order ->
                    var showStatusMenu by remember { mutableStateOf(false) }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Order #${order.id}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = WashdayTextPrimary)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(order.customerName, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = WashdayBlue)
                            }
                            Text("${order.serviceName} • ${order.weightKg} kg • ${order.contactNumber}", fontSize = 12.sp, color = WashdayTextMuted)
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Status Picker
                            Box {
                                val statusBg = when (order.status) {
                                    "Completed", "Ready for Pickup" -> WashdayGreenLight
                                    "Washing", "Drying" -> WashdayBlueLight
                                    else -> WashdayAmberLight
                                }
                                val statusFg = when (order.status) {
                                    "Completed", "Ready for Pickup" -> WashdayGreen
                                    "Washing", "Drying" -> WashdayBlue
                                    else -> WashdayAmber
                                }

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(statusBg)
                                        .clickable { showStatusMenu = true }
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Text("${order.status} ▾", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = statusFg)
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
                                                onShowToast("Order #${order.id} updated to $st")
                                            }
                                        )
                                    }
                                }
                            }

                            IconButton(
                                onClick = { selectedOrderForReceipt = order },
                                modifier = Modifier.size(36.dp).clip(CircleShape).background(WashdayCanvas)
                            ) {
                                Icon(Icons.AutoMirrored.Filled.ReceiptLong, contentDescription = "Receipt", tint = WashdayTextPrimary, modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                    HorizontalDivider(color = WashdayLine)
                }
            }
        }
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
