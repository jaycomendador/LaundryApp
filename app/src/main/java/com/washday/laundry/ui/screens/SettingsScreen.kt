package com.washday.laundry.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.LocalLaundryService
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
fun SettingsScreen(
    onNavigateToTab: (ShopTab) -> Unit,
    onExportBackup: () -> String,
    onImportRestore: (String) -> Boolean,
    onLogout: () -> Unit,
    onShowToast: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var backupText by remember { mutableStateOf("") }
    var showBackupArea by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .background(LaundryMateCanvas)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column {
            Text("Settings & Management", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = LaundryTextPrimary)
            Text("Manage shop services, analytics, backup & account settings.", fontSize = 13.sp, color = LaundryTextMuted)
        }

        // Staff Profile Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(LaundryPinkLight),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Storefront, contentDescription = null, tint = LaundryPinkPrimary, modifier = Modifier.size(26.dp))
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text("Laundry Mate Staff", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = LaundryTextPrimary)
                    Text("Admin • Staff Member", fontSize = 12.sp, color = LaundryTextMuted)
                }
            }
        }

        // Shop Management Modules
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text("Shop Management Modules", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = LaundryTextPrimary)

                SettingsModuleRow("Sales Summary & Revenue Reports", "View daily revenue & category splits", Icons.Default.BarChart) {
                    onNavigateToTab(ShopTab.SALES)
                }

                HorizontalDivider(color = LaundryMateLine)

                SettingsModuleRow("Customer History Directory", "View total customer orders & spend totals", Icons.Default.People) {
                    onNavigateToTab(ShopTab.CUSTOMERS)
                }

                HorizontalDivider(color = LaundryMateLine)

                SettingsModuleRow("Services & Rates Catalog", "Manage service options (Regular, Executive, Bulk, Dry Clean)", Icons.Default.LocalLaundryService) {
                    onNavigateToTab(ShopTab.SERVICES)
                }
            }
        }

        // Backup & Restore Section
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Database Backup & Restore", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = LaundryTextPrimary)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = {
                            backupText = onExportBackup()
                            showBackupArea = true
                            onShowToast("Database JSON exported successfully!")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = LaundryPinkPrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f).height(46.dp)
                    ) {
                        Icon(Icons.Default.CloudDownload, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Export Backup", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = { showBackupArea = !showBackupArea },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f).height(46.dp)
                    ) {
                        Icon(Icons.Default.CloudUpload, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Restore Data", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = LaundryTextPrimary)
                    }
                }

                if (showBackupArea) {
                    OutlinedTextField(
                        value = backupText,
                        onValueChange = { backupText = it },
                        label = { Text("Backup JSON String") },
                        placeholder = { Text("Paste exported JSON data here to restore...") },
                        maxLines = 6,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            if (backupText.isNotBlank()) {
                                val success = onImportRestore(backupText)
                                if (success) {
                                    onShowToast("Laundry records restored from backup!")
                                } else {
                                    onShowToast("Invalid backup JSON format!")
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = LaundryPinkPrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().height(46.dp)
                    ) {
                        Text("Import & Restore", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Account Sign Out
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, LaundryMateLine, RoundedCornerShape(12.dp))
                        .clickable { onLogout() }
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null, tint = LaundryPinkPrimary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Sign Out Account", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = LaundryTextPrimary)
                    }
                    Text("Logout ➔", fontSize = 13.sp, color = LaundryTextMuted)
                }
            }
        }
    }
}

@Composable
fun SettingsModuleRow(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(LaundryPinkLight),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = title, tint = LaundryPinkPrimary, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = LaundryTextPrimary)
                Text(subtitle, fontSize = 12.sp, color = LaundryTextMuted)
            }
        }

        Text("View ➔", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = LaundryPinkPrimary)
    }
}
