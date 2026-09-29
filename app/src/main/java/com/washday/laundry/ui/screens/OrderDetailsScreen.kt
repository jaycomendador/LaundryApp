package com.washday.laundry.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import com.washday.laundry.ui.theme.LaundryGreen
import com.washday.laundry.ui.theme.LaundryMateCanvas
import com.washday.laundry.ui.theme.LaundryMateLine
import com.washday.laundry.ui.theme.LaundryPinkLight
import com.washday.laundry.ui.theme.LaundryPinkPrimary
import com.washday.laundry.ui.theme.LaundryTextMuted
import com.washday.laundry.ui.theme.LaundryTextPrimary

@Composable
fun OrderDetailsScreen(
    order: LaundryOrder,
    onUpdateStatus: (String, String) -> Unit,
    onBack: () -> Unit,
    onShowToast: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val statusSteps = listOf("Received", "Washing", "Drying", "Ready for Pickup", "Completed")
    val currentStepIndex = statusSteps.indexOf(order.status).coerceAtLeast(0)

    Column(
        modifier = modifier
            .background(LaundryMateCanvas)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Order Details #${order.id}", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = LaundryTextPrimary)
                Text("${order.customerName} • ${order.date}", fontSize = 13.sp, color = LaundryTextMuted)
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(LaundryPinkLight)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(order.status, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = LaundryPinkPrimary)
            }
        }

        // Order Summary Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Order Information", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = LaundryTextPrimary)
                HorizontalDivider(color = LaundryMateLine)

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Service:", color = LaundryTextMuted, fontSize = 13.sp)
                    Text(order.serviceName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Weight:", color = LaundryTextMuted, fontSize = 13.sp)
                    Text("${order.weightKg} kg", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Total Price:", color = LaundryTextMuted, fontSize = 13.sp)
                    Text("₱${order.totalAmount.toInt()}.00", fontWeight = FontWeight.ExtraBold, color = LaundryPinkPrimary, fontSize = 16.sp)
                }
            }
        }

        // Laundry Status Stepper Checklist
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Laundry Progress Stepper", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = LaundryTextPrimary)

                statusSteps.forEachIndexed { idx, st ->
                    val isDone = idx <= currentStepIndex

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onUpdateStatus(order.id, st)
                                onShowToast("Status updated to $st")
                            }
                            .padding(vertical = 6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(if (isDone) LaundryPinkPrimary else LaundryMateCanvas)
                                .border(1.dp, if (isDone) LaundryPinkPrimary else LaundryMateLine, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isDone) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Text(
                            text = st,
                            fontSize = 14.sp,
                            fontWeight = if (isDone) FontWeight.Bold else FontWeight.Normal,
                            color = if (isDone) LaundryTextPrimary else LaundryTextMuted
                        )
                    }

                    if (idx < statusSteps.size - 1) {
                        HorizontalDivider(color = LaundryMateLine, modifier = Modifier.padding(start = 38.dp))
                    }
                }
            }
        }

        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(containerColor = LaundryPinkPrimary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Back to Orders", fontWeight = FontWeight.Bold)
        }
    }
}
