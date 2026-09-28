package com.washday.laundry.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalLaundryService
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
import com.washday.laundry.ui.theme.LaundryMateCanvas
import com.washday.laundry.ui.theme.LaundryMateLine
import com.washday.laundry.ui.theme.LaundryPinkLight
import com.washday.laundry.ui.theme.LaundryPinkPrimary
import com.washday.laundry.ui.theme.LaundryTextMuted
import com.washday.laundry.ui.theme.LaundryTextPrimary

@Composable
fun ShopHeader(
    notifications: List<AppNotification>,
    onMarkNotificationsRead: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showNotifMenu by remember { mutableStateOf(false) }
    val hasUnread = notifications.any { !it.isRead }

    Surface(
        color = LaundryMateCanvas,
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Laundry Mate Logo & Title
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(LaundryPinkPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.LocalLaundryService,
                            contentDescription = "Logo",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = "Laundry Mate",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = LaundryPinkPrimary,
                        letterSpacing = (-0.5).sp
                    )
                    Text(
                        text = "Clean Clothes Happy Days",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = LaundryTextMuted
                    )
                }
            }

            // Notification & Profile Badge
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box {
                    IconButton(
                        onClick = {
                            showNotifMenu = true
                            onMarkNotificationsRead()
                        },
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(1.dp, LaundryMateLine, CircleShape)
                    ) {
                        BadgedBox(
                            badge = {
                                if (hasUnread) {
                                    Badge(
                                        containerColor = LaundryPinkPrimary,
                                        modifier = Modifier.size(8.dp)
                                    )
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                tint = LaundryTextPrimary,
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
                            text = "Notifications",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = LaundryTextPrimary,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                        )

                        if (notifications.isEmpty()) {
                            DropdownMenuItem(
                                text = { Text("No new notifications", fontSize = 13.sp, color = LaundryTextMuted) },
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
                                                color = LaundryTextPrimary
                                            )
                                            Text(
                                                text = notif.message,
                                                fontSize = 12.sp,
                                                color = LaundryTextMuted
                                            )
                                        }
                                    },
                                    onClick = { showNotifMenu = false }
                                )
                            }
                        }
                    }
                }

                // Profile Badge
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .border(1.dp, LaundryMateLine, RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(CircleShape)
                            .background(LaundryPinkLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Storefront,
                            contentDescription = "Staff Profile",
                            tint = LaundryPinkPrimary,
                            modifier = Modifier.size(13.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text(
                            text = "Staff",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = LaundryTextPrimary
                        )
                        Text(
                            text = "Admin",
                            fontSize = 10.sp,
                            color = LaundryTextMuted
                        )
                    }
                }
            }
        }
    }
}
