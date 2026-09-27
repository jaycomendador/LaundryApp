package com.washday.laundry.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.washday.laundry.model.LaundryService
import com.washday.laundry.ui.theme.WashdayBlue
import com.washday.laundry.ui.theme.WashdayBlueLight
import com.washday.laundry.ui.theme.WashdayCanvas
import com.washday.laundry.ui.theme.WashdayLine
import com.washday.laundry.ui.theme.WashdayTextMuted
import com.washday.laundry.ui.theme.WashdayTextPrimary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NewOrderScreen(
    services: List<LaundryService>,
    onSaveOrder: (String, String, LaundryService, Int) -> Unit,
    onShowToast: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var customerName by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    var selectedServiceIndex by remember { mutableIntStateOf(0) }
    var weightKg by remember { mutableIntStateOf(4) }

    val selectedService = services.getOrElse(selectedServiceIndex) { services.first() }
    val calculatedTotal = selectedService.pricePerKg * weightKg

    Column(
        modifier = modifier
            .background(WashdayCanvas)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column {
            Text("Create New Order", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = WashdayTextPrimary)
            Text("Enter customer info and select laundry care services.", fontSize = 13.sp, color = WashdayTextMuted)
        }

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Customer Information", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = WashdayTextPrimary)

                OutlinedTextField(
                    value = customerName,
                    onValueChange = { customerName = it },
                    label = { Text("Customer Name") },
                    placeholder = { Text("e.g. Lena Morales") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = contactNumber,
                    onValueChange = { contactNumber = it },
                    label = { Text("Contact Number") },
                    placeholder = { Text("+63 912 345 6789") },
                    modifier = Modifier.fillMaxWidth()
                )

                HorizontalDivider(color = WashdayLine)

                Text("Select Service", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = WashdayTextPrimary)

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    services.forEachIndexed { idx, service ->
                        val isSelected = idx == selectedServiceIndex
                        val bg = if (isSelected) WashdayBlueLight else WashdayCanvas
                        val borderClr = if (isSelected) WashdayBlue else WashdayLine

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(bg)
                                .border(1.5.dp, borderClr, RoundedCornerShape(12.dp))
                                .clickable { selectedServiceIndex = idx }
                                .padding(horizontal = 14.dp, vertical = 10.dp)
                        ) {
                            Column {
                                Text(
                                    text = service.name,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = WashdayTextPrimary
                                )
                                Text(
                                    text = "₱${service.pricePerKg}/kg",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = WashdayBlue
                                )
                            }
                        }
                    }
                }

                HorizontalDivider(color = WashdayLine)

                // Weight Selector
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Laundry Weight", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = WashdayTextPrimary)
                        Text("Enter weight in kilograms", fontSize = 12.sp, color = WashdayTextMuted)
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        IconButton(
                            onClick = { if (weightKg > 1) weightKg-- },
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(WashdayCanvas)
                                .border(1.dp, WashdayLine, CircleShape)
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = "Decrease", tint = WashdayTextPrimary, modifier = Modifier.size(16.dp))
                        }

                        Text(
                            text = "$weightKg kg",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = WashdayTextPrimary
                        )

                        IconButton(
                            onClick = { weightKg++ },
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(WashdayCanvas)
                                .border(1.dp, WashdayLine, CircleShape)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Increase", tint = WashdayTextPrimary, modifier = Modifier.size(16.dp))
                        }
                    }
                }

                HorizontalDivider(color = WashdayLine)

                // Automatic Price Calculation
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(WashdayBlueLight)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("TOTAL AMOUNT", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = WashdayBlue)
                        Text("₱$calculatedTotal.00", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = WashdayBlue)
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(
                            onClick = { onShowToast("Order saved as draft") },
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Save draft", fontSize = 12.sp, color = WashdayTextPrimary)
                        }

                        Button(
                            onClick = {
                                val name = if (customerName.isBlank()) "Walk-in Customer" else customerName
                                val contact = if (contactNumber.isBlank()) "+63 900 000 0000" else contactNumber
                                onSaveOrder(name, contact, selectedService, weightKg)
                                customerName = ""
                                contactNumber = ""
                                onShowToast("New order created! Digital receipt ready.")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = WashdayBlue),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Save Order", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
