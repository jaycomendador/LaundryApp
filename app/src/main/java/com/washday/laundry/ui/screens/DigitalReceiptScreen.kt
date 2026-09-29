package com.washday.laundry.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalLaundryService
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.washday.laundry.model.LaundryOrder
import com.washday.laundry.ui.theme.LaundryMateCanvas
import com.washday.laundry.ui.theme.LaundryMateLine
import com.washday.laundry.ui.theme.LaundryPinkLight
import com.washday.laundry.ui.theme.LaundryPinkPrimary
import com.washday.laundry.ui.theme.LaundryTextMuted
import com.washday.laundry.ui.theme.LaundryTextPrimary

@Composable
fun DigitalReceiptScreen(
    order: LaundryOrder,
    onBack: () -> Unit,
    onShowToast: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(LaundryMateCanvas)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Laundry Mate Header Logo
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(LaundryPinkPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.LocalLaundryService, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                            Icon(Icons.Default.Favorite, contentDescription = null, tint = Color.White, modifier = Modifier.size(8.dp))
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Laundry Mate", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = LaundryPinkPrimary)
                }

                Text("Digital Receipt", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = LaundryTextPrimary)

                HorizontalDivider(color = LaundryMateLine)

                // Order Meta Information
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Order No:", fontSize = 13.sp, color = LaundryTextMuted)
                    Text("#${order.id}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = LaundryTextPrimary)
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Date:", fontSize = 13.sp, color = LaundryTextMuted)
                    Text("${order.date} ${order.time}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = LaundryTextPrimary)
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Customer:", fontSize = 13.sp, color = LaundryTextMuted)
                    Text(order.customerName, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = LaundryTextPrimary)
                }

                HorizontalDivider(color = LaundryMateLine)

                // Table Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(LaundryPinkLight)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Service", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = LaundryPinkPrimary, modifier = Modifier.weight(1.5f))
                    Text("Weight", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = LaundryPinkPrimary, modifier = Modifier.weight(1f))
                    Text("Amount", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = LaundryPinkPrimary, modifier = Modifier.weight(1f))
                }

                // Table Row
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(order.serviceName, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = LaundryTextPrimary, modifier = Modifier.weight(1.5f))
                    Text("${order.weightKg} kg", fontSize = 13.sp, color = LaundryTextMuted, modifier = Modifier.weight(1f))
                    Text("₱${order.totalAmount.toInt()}.00", fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = LaundryPinkPrimary, modifier = Modifier.weight(1f))
                }

                HorizontalDivider(color = LaundryMateLine)

                // Total Price Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("TOTAL PAID", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = LaundryTextPrimary)
                    Text("₱${order.totalAmount.toInt()}.00", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = LaundryPinkPrimary)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Thank you for choosing Laundry Mate!",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = LaundryTextMuted
                )

                HorizontalDivider(color = LaundryMateLine)

                // Action Buttons: Share & Download
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = { onShowToast("Receipt shared!") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f).height(46.dp)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Share", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = LaundryTextPrimary)
                    }

                    Button(
                        onClick = { onShowToast("Receipt downloaded!") },
                        colors = ButtonDefaults.buttonColors(containerColor = LaundryPinkPrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f).height(46.dp)
                    ) {
                        Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Download", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        OutlinedButton(
            onClick = onBack,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text("Back to Dashboard", fontWeight = FontWeight.Bold, color = LaundryTextPrimary)
        }
    }
}
