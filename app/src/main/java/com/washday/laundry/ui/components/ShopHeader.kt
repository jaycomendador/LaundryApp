package com.washday.laundry.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
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
import com.washday.laundry.model.AppNotification
import com.washday.laundry.ui.theme.WashdayBlue
import com.washday.laundry.ui.theme.WashdayBlueLight
import com.washday.laundry.ui.theme.WashdayCanvas
import com.washday.laundry.ui.theme.WashdayLine
import com.washday.laundry.ui.theme.WashdayTextMuted
import com.washday.laundry.ui.theme.WashdayTextPrimary

@Composable
fun ShopHeader(
    notifications: List<AppNotification>,
    onMarkNotificationsRead: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showNotifMenu by remember { mutableStateOf(false) }
    val hasUnread = notifications.any { !it.isRead }

    Surface(
        color = WashdayCanvas,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Shop Brand
            Column {
                Text(
                    text = "Washday",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = WashdayTextPrimary,
                    letterSpacing = (-0.5).sp
                )
                Text(
                    text = "Laundry Management Portal",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = WashdayTextMuted
                )
            }

            // Notification & Shop Manager Profile
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Notification Button
                Box {
                    IconButton(
                        onClick = {
                            showNotifMenu = true
                            onMarkNotificationsRead()
                        },
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(1.dp, WashdayLine, CircleShape)
                    ) {
                        BadgedBox(
                            badge = {
                                if (hasUnread) {
                                    Badge(
                                        containerColor = WashdayBlue,
                                        modifier = Modifier.size(8.dp)
                                    )
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                tint = WashdayTextPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = showNotifMenu,
                        onDismissRequest = { showNotifMenu = false },
                        modifier = Modifier
                            .width(280.dp)
                            .background(Color.White)
                    ) {
                        Text(
                            text = "Shop Notifications",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = WashdayTextPrimary,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                        )

                        if (notifications.isEmpty()) {
                            DropdownMenuItem(
                                text = { Text("No new notifications", fontSize = 13.sp, color = WashdayTextMuted) },
                                onClick = { showNotifMenu = false }
                            )
                        } else {
                            notifications.forEach { notif ->
                                DropdownMenuItem(
                                    text = {
                                        Column {
                                            Text(
                                                text = notif.title,
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = WashdayTextPrimary
                                            )
                                            Text(
                                                text = notif.message,
                                                fontSize = 12.sp,
                                                color = WashdayTextMuted
                                            )
                                        }
                                    },
                                    onClick = { showNotifMenu = false }
                                )
                            }
                        }
                    }
                }

                // Manager Profile Badge
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .border(1.dp, WashdayLine, RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(WashdayBlueLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Storefront,
                            contentDescription = "Shop Manager",
                            tint = WashdayBlue,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Lena Morales",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = WashdayTextPrimary
                        )
                        Text(
                            text = "Shop manager",
                            fontSize = 10.sp,
                            color = WashdayTextMuted
                        )
                    }
                }
            }
        }
    }
}
