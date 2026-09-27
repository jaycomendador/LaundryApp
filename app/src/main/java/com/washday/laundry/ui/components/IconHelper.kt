package com.washday.laundry.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Iron
import androidx.compose.material.icons.filled.LocalLaundryService
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.LockClock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun getServiceIcon(iconName: String): ImageVector {
    return when (iconName) {
        "sparkles" -> Icons.Default.AutoAwesome
        "wind" -> Icons.Default.Air
        "bag" -> Icons.Default.ShoppingBag
        "iron" -> Icons.Default.Iron
        "washer" -> Icons.Default.LocalLaundryService
        "home" -> Icons.Default.Home
        "receipt" -> Icons.AutoMirrored.Filled.ReceiptLong
        "calendar" -> Icons.Default.CalendarMonth
        "card" -> Icons.Default.CreditCard
        "bell" -> Icons.Default.Notifications
        "truck" -> Icons.Default.LocalShipping
        "clock" -> Icons.Default.LockClock
        "pin" -> Icons.Default.LocationOn
        "plus" -> Icons.Default.Add
        "minus" -> Icons.Default.Remove
        "check" -> Icons.Default.Check
        "chart" -> Icons.Default.BarChart
        "user" -> Icons.Default.Person
        "logout" -> Icons.AutoMirrored.Filled.Logout
        else -> Icons.Default.LocalLaundryService
    }
}
