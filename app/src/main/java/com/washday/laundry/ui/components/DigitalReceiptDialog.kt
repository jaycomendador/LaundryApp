package com.washday.laundry.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.Print
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.washday.laundry.model.LaundryOrder
import com.washday.laundry.ui.theme.WashdayBlue
import com.washday.laundry.ui.theme.WashdayGreen
import com.washday.laundry.ui.theme.WashdayLine
import com.washday.laundry.ui.theme.WashdayTextMuted

@Composable
fun DigitalReceiptDialog(
    order: LaundryOrder?,
    onDismiss: () -> Unit,
    onPrint: () -> Unit
) {
    order?.let { ord ->
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.AutoMirrored.Filled.ReceiptLong, contentDescription = null, tint = WashdayBlue)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Digital Receipt #${ord.id}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("WASHDAY LAUNDRY SHOP", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = WashdayTextMuted, letterSpacing = 1.sp)
                    HorizontalDivider(color = WashdayLine)

                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("Customer:", color = WashdayTextMuted, fontSize = 13.sp)
                        Text(ord.customerName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("Contact:", color = WashdayTextMuted, fontSize = 13.sp)
                        Text(ord.contactNumber, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("Service:", color = WashdayTextMuted, fontSize = 13.sp)
                        Text(ord.serviceName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("Weight:", color = WashdayTextMuted, fontSize = 13.sp)
                        Text("${ord.weightKg} kg", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("Status:", color = WashdayTextMuted, fontSize = 13.sp)
                        Text(ord.status, fontWeight = FontWeight.Bold, color = WashdayGreen, fontSize = 13.sp)
                    }

                    HorizontalDivider(color = WashdayLine)

                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("TOTAL PAID:", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("₱${ord.totalAmount.toInt()}.00", fontWeight = FontWeight.ExtraBold, color = WashdayBlue, fontSize = 18.sp)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = onPrint,
                    colors = ButtonDefaults.buttonColors(containerColor = WashdayBlue)
                ) {
                    Icon(Icons.Default.Print, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Print Receipt")
                }
            }
        )
    }
}
