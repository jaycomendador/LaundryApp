package com.washday.laundry.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
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
import com.washday.laundry.ui.components.getServiceIcon
import com.washday.laundry.ui.theme.LaundryMateCanvas
import com.washday.laundry.ui.theme.LaundryMateLine
import com.washday.laundry.ui.theme.LaundryPinkLight
import com.washday.laundry.ui.theme.LaundryPinkPrimary
import com.washday.laundry.ui.theme.LaundryTextMuted
import com.washday.laundry.ui.theme.LaundryTextPrimary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NewOrderFlowScreen(
    services: List<LaundryService>,
    onSaveOrder: (String, String, LaundryService, Double, Double) -> Unit,
    onShowToast: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var flowStep by remember { mutableIntStateOf(1) } // 1: Customer Info, 2: Service Select, 3: Confirmation

    var customerName by remember { mutableStateOf("Juan Dela Cruz") }
    var contactNumber by remember { mutableStateOf("0917 123 4567") }
    var weightKg by remember { mutableDoubleStateOf(2.0) }
    var selectedServiceIndex by remember { mutableIntStateOf(0) }
    var additionalCharges by remember { mutableDoubleStateOf(0.0) }

    val selectedService = services.getOrElse(selectedServiceIndex) { services.first() }
    val basePrice = selectedService.pricePerKg * weightKg
    val calculatedTotal = basePrice + additionalCharges

    Column(
        modifier = modifier
            .background(LaundryMateCanvas)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column {
            Text("New Laundry Order", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = LaundryTextPrimary)
            Text("Step $flowStep of 3: ${if (flowStep == 1) "Customer Details" else if (flowStep == 2) "Service Selection" else "Order Summary"}", fontSize = 13.sp, color = LaundryTextMuted)
        }

        when (flowStep) {
            1 -> {
                // Step 1: Customer Information
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text("Customer Information", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = LaundryTextPrimary)

                        OutlinedTextField(
                            value = customerName,
                            onValueChange = { customerName = it },
                            label = { Text("Customer Name") },
                            placeholder = { Text("Juan Dela Cruz") },
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = LaundryPinkPrimary) },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = contactNumber,
                            onValueChange = { contactNumber = it },
                            label = { Text("Phone Number") },
                            placeholder = { Text("0917 123 4567") },
                            leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = LaundryPinkPrimary) },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        )

                        HorizontalDivider(color = LaundryMateLine)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Laundry Weight", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = LaundryTextPrimary)
                                Text("Enter weight in kilograms", fontSize = 12.sp, color = LaundryTextMuted)
                            }

                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                IconButton(
                                    onClick = { if (weightKg > 0.5) weightKg -= 0.5 },
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(LaundryMateCanvas)
                                        .border(1.dp, LaundryMateLine, CircleShape)
                                ) {
                                    Icon(Icons.Default.Remove, contentDescription = null, tint = LaundryTextPrimary, modifier = Modifier.size(16.dp))
                                }

                                Text("$weightKg kg", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = LaundryTextPrimary)

                                IconButton(
                                    onClick = { weightKg += 0.5 },
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(LaundryMateCanvas)
                                        .border(1.dp, LaundryMateLine, CircleShape)
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = null, tint = LaundryTextPrimary, modifier = Modifier.size(16.dp))
                                }
                            }
                        }

                        Button(
                            onClick = { flowStep = 2 },
                            colors = ButtonDefaults.buttonColors(containerColor = LaundryPinkPrimary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        ) {
                            Text("Next: Select Service", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            2 -> {
                // Step 2: Service Selection
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text("Select Service", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = LaundryTextPrimary)

                        services.forEachIndexed { idx, service ->
                            val isSelected = idx == selectedServiceIndex
                            val bg = if (isSelected) LaundryPinkLight else LaundryMateCanvas
                            val borderClr = if (isSelected) LaundryPinkPrimary else LaundryMateLine

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(bg)
                                    .border(1.5.dp, borderClr, RoundedCornerShape(14.dp))
                                    .clickable { selectedServiceIndex = idx }
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(Color.White),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(getServiceIcon(service.iconName), contentDescription = null, tint = LaundryPinkPrimary, modifier = Modifier.size(20.dp))
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(service.name, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = LaundryTextPrimary)
                                        service.detail?.let { Text(it, fontSize = 12.sp, color = LaundryTextMuted) }
                                    }
                                }

                                Text("₱${service.pricePerKg.toInt()}.00/kg", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = LaundryPinkPrimary)
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = { flowStep = 1 },
                                colors = ButtonDefaults.buttonColors(containerColor = LaundryMateCanvas, contentColor = LaundryTextPrimary),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                            ) {
                                Text("Back", fontWeight = FontWeight.Bold)
                            }

                            Button(
                                onClick = { flowStep = 3 },
                                colors = ButtonDefaults.buttonColors(containerColor = LaundryPinkPrimary),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                            ) {
                                Text("Calculate Price", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            3 -> {
                // Step 3: Order Confirmation
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text("Order Summary", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = LaundryTextPrimary)

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Customer:", color = LaundryTextMuted, fontSize = 13.sp)
                            Column(horizontalAlignment = Alignment.End) {
                                Text(customerName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text(contactNumber, fontSize = 11.sp, color = LaundryTextMuted)
                            }
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Service:", color = LaundryTextMuted, fontSize = 13.sp)
                            Text("${selectedService.name} • ₱${selectedService.pricePerKg.toInt()}/kg", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Weight:", color = LaundryTextMuted, fontSize = 13.sp)
                            Text("$weightKg kg", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }

                        HorizontalDivider(color = LaundryMateLine)

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Base Price:", color = LaundryTextMuted, fontSize = 13.sp)
                            Text("₱${basePrice.toInt()}.00", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Additional Charges:", color = LaundryTextMuted, fontSize = 13.sp)
                            Text("₱${additionalCharges.toInt()}.00", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(LaundryPinkLight)
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("TOTAL PRICE", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = LaundryPinkPrimary)
                            Text("₱${calculatedTotal.toInt()}.00", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = LaundryPinkPrimary)
                        }

                        Button(
                            onClick = {
                                onSaveOrder(customerName, contactNumber, selectedService, weightKg, additionalCharges)
                                onShowToast("Order saved to database!")
                                flowStep = 1
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = LaundryPinkPrimary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text("Save Order", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
