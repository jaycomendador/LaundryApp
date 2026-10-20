package com.washday.laundry.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalLaundryService
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.washday.laundry.model.ShopTab
import com.washday.laundry.ui.theme.LaundryMateCanvas
import com.washday.laundry.ui.theme.LaundryMateLine
import com.washday.laundry.ui.theme.LaundryPinkLight
import com.washday.laundry.ui.theme.LaundryPinkPrimary
import com.washday.laundry.ui.theme.LaundryTextMuted
import com.washday.laundry.ui.theme.LaundryTextPrimary

@Composable
fun getShopTabIcon(tab: ShopTab): ImageVector {
    return when (tab) {
        ShopTab.DASHBOARD -> Icons.Default.Home
        ShopTab.NEW_ORDER -> Icons.Default.Add
        ShopTab.ORDERS -> Icons.AutoMirrored.Filled.ListAlt
        ShopTab.COMPLETED_HISTORY -> Icons.Default.History
        ShopTab.SETTINGS -> Icons.Default.Settings
        ShopTab.SALES -> Icons.Default.BarChart
        ShopTab.CUSTOMERS -> Icons.Default.People
        ShopTab.SERVICES -> Icons.Default.LocalLaundryService
    }
}

@Composable
fun ShopBottomNavigation(
    selectedTab: ShopTab,
    onTabSelected: (ShopTab) -> Unit,
    modifier: Modifier = Modifier
) {
    // Exactly 4 Clean Organized Bottom Navbar Items
    val mainBottomTabs = listOf(
        ShopTab.DASHBOARD,
        ShopTab.NEW_ORDER,
        ShopTab.ORDERS,
        ShopTab.COMPLETED_HISTORY
    )

    Surface(
        color = Color.White,
        shadowElevation = 8.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        NavigationBar(
            containerColor = Color.White,
            contentColor = LaundryTextPrimary,
            tonalElevation = 0.dp,
            modifier = Modifier.fillMaxWidth().height(68.dp)
        ) {
            mainBottomTabs.forEach { tab ->
                val isSelected = tab == selectedTab
                val isNewOrder = tab == ShopTab.NEW_ORDER

                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onTabSelected(tab) },
                    icon = {
                        if (isNewOrder) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(LaundryPinkPrimary),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = tab.title,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        } else {
                            Icon(
                                imageVector = getShopTabIcon(tab),
                                contentDescription = tab.title,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    },
                    label = {
                        Text(
                            text = tab.title,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = LaundryPinkPrimary,
                        selectedTextColor = LaundryPinkPrimary,
                        indicatorColor = LaundryPinkLight,
                        unselectedIconColor = LaundryTextMuted,
                        unselectedTextColor = LaundryTextMuted
                    )
                )
            }
        }
    }
}

@Composable
fun ShopSidebarNavigation(
    selectedTab: ShopTab,
    onTabSelected: (ShopTab) -> Unit,
    onSignOut: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sidebarTabs = listOf(
        ShopTab.DASHBOARD,
        ShopTab.NEW_ORDER,
        ShopTab.ORDERS,
        ShopTab.COMPLETED_HISTORY,
        ShopTab.SERVICES,
        ShopTab.CUSTOMERS,
        ShopTab.SALES,
        ShopTab.SETTINGS
    )

    Surface(
        color = Color.White,
        modifier = modifier
            .width(260.dp)
            .fillMaxHeight()
            .border(1.dp, LaundryMateLine)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
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
                    Column {
                        Text("Laundry Mate", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = LaundryPinkPrimary)
                        Text("Management Portal", fontSize = 11.sp, color = LaundryTextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                sidebarTabs.forEach { tab ->
                    val isSelected = tab == selectedTab
                    val bg = if (isSelected) LaundryPinkLight else Color.Transparent
                    val fg = if (isSelected) LaundryPinkPrimary else LaundryTextPrimary

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(bg)
                            .clickable { onTabSelected(tab) }
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(getShopTabIcon(tab), contentDescription = tab.title, tint = fg, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(tab.title, fontSize = 14.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium, color = fg)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(LaundryMateCanvas)
                    .clickable { onSignOut() }
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Sign Out", tint = LaundryTextMuted, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text("Sign Out", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = LaundryTextMuted)
            }
        }
    }
}
