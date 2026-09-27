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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.washday.laundry.model.LaundryService
import com.washday.laundry.ui.components.getServiceIcon
import com.washday.laundry.ui.theme.WashdayBlue
import com.washday.laundry.ui.theme.WashdayBlueLight
import com.washday.laundry.ui.theme.WashdayCanvas
import com.washday.laundry.ui.theme.WashdayLine
import com.washday.laundry.ui.theme.WashdayTextMuted
import com.washday.laundry.ui.theme.WashdayTextPrimary

@Composable
fun ServicesCatalogScreen(
    services: List<LaundryService>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(WashdayCanvas)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column {
            Text("Service Catalog", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = WashdayTextPrimary)
            Text("Simple pricing for every kind of laundry care.", fontSize = 13.sp, color = WashdayTextMuted)
        }

        services.forEach { service ->
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(WashdayBlueLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = getServiceIcon(service.iconName),
                                contentDescription = service.name,
                                tint = WashdayBlue,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(
                                text = service.name,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = WashdayTextPrimary
                            )
                            service.detail?.let { detail ->
                                Text(
                                    text = detail,
                                    fontSize = 12.sp,
                                    color = WashdayTextMuted
                                )
                            }
                        }
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "₱${service.pricePerKg}.00",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = WashdayBlue
                        )
                        Text(
                            text = "per kilogram",
                            fontSize = 11.sp,
                            color = WashdayTextMuted
                        )
                    }
                }
            }
        }
    }
}
