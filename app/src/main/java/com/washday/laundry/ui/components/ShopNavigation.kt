package com.washday.laundry.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.washday.laundry.model.ShopTab
import com.washday.laundry.ui.theme.WashdayBlue
import com.washday.laundry.ui.theme.WashdayBlueLight
import com.washday.laundry.ui.theme.WashdayLine
import com.washday.laundry.ui.theme.WashdayTextMuted
import com.washday.laundry.ui.theme.WashdayTextPrimary

@Composable
fun ShopBottomNavigation(
    selectedTab: ShopTab,
    onTabSelected: (ShopTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.White,
        shadowElevation = 8.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ShopTab.entries.forEach { tab ->
                val isSelected = tab == selectedTab
                val bg = if (isSelected) WashdayBlueLight else Color.Transparent
                val fg = if (isSelected) WashdayBlue else WashdayTextMuted

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(bg)
                        .clickable { onTabSelected(tab) }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = getServiceIcon(tab.iconName),
                        contentDescription = tab.title,
                        tint = fg,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = tab.title,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = fg
                    )
                }
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
    Surface(
        color = Color.White,
        modifier = modifier
            .width(220.dp)
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Washday",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = WashdayTextPrimary
                )
                Text(
                    text = "Laundry Management",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = WashdayTextMuted
                )

                Spacer(modifier = Modifier.height(32.dp))

                ShopTab.entries.forEach { tab ->
                    val isSelected = tab == selectedTab
                    val bg = if (isSelected) WashdayBlueLight else Color.Transparent
                    val fg = if (isSelected) WashdayBlue else WashdayTextMuted

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(bg)
                            .clickable { onTabSelected(tab) }
                            .padding(horizontal = 14.dp, vertical = 12.dp)
                    ) {
                        Icon(
                            imageVector = getServiceIcon(tab.iconName),
                            contentDescription = tab.title,
                            tint = fg,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = tab.title,
                            fontSize = 14.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = fg
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }

            Column {
                HorizontalDivider(color = WashdayLine, modifier = Modifier.padding(vertical = 16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { onSignOut() }
                        .padding(horizontal = 14.dp, vertical = 12.dp)
                ) {
                    Icon(
                        imageVector = getServiceIcon("logout"),
                        contentDescription = "Sign out",
                        tint = WashdayTextMuted,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Sign out",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = WashdayTextMuted
                    )
                }
            }
        }
    }
}
