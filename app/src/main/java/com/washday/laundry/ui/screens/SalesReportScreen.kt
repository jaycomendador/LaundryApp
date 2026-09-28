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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.washday.laundry.model.DailySalesSummary
import com.washday.laundry.model.LaundryOrder
import com.washday.laundry.model.ServiceSalesSplit
import com.washday.laundry.model.WeeklySalesPoint
import com.washday.laundry.ui.theme.LaundryMateCanvas
import com.washday.laundry.ui.theme.LaundryMateLine
import com.washday.laundry.ui.theme.LaundryPinkPrimary
import com.washday.laundry.ui.theme.LaundryTextMuted
import com.washday.laundry.ui.theme.LaundryTextPrimary

@Composable
fun SalesReportScreen(
    dailySalesSummary: DailySalesSummary,
    weeklySalesPoints: List<WeeklySalesPoint>,
    serviceSalesSplit: List<ServiceSalesSplit>,
    orders: List<LaundryOrder>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(LaundryMateCanvas)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column {
            Text("Sales Report", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = LaundryTextPrimary)
            Text("Monitor daily revenue performance and weekly sales split.", fontSize = 13.sp, color = LaundryTextMuted)
        }

        // Revenue Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = LaundryPinkPrimary),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("TODAY'S TOTAL REVENUE", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White.copy(alpha = 0.8f))
                Spacer(modifier = Modifier.height(6.dp))
                Text("₱${dailySalesSummary.totalRevenueToday.toInt()}.00", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Text("${dailySalesSummary.completedCount} orders completed today • +12% vs yesterday", fontSize = 13.sp, color = Color.White.copy(alpha = 0.9f))
            }
        }

        // Sales This Week Bar Chart
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column {
                    Text("Sales This Week", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = LaundryTextPrimary)
                    Text("Revenue performance over the last 7 days", fontSize = 12.sp, color = LaundryTextMuted)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    weeklySalesPoints.forEach { pt ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            val h = (pt.revenue / 10500.0 * 100).dp
                            Box(
                                modifier = Modifier
                                    .width(24.dp)
                                    .height(h)
                                    .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                                    .background(LaundryPinkPrimary)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(pt.day, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = LaundryTextMuted)
                        }
                    }
                }
            }
        }

        // Sales by Service Split
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Column {
                    Text("Sales by Service", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = LaundryTextPrimary)
                    Text("Today's revenue split by category", fontSize = 12.sp, color = LaundryTextMuted)
                }

                serviceSalesSplit.forEach { split ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(split.serviceName, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = LaundryTextPrimary)
                        Text("${split.percentage}% (₱${split.amount.toInt()})", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = LaundryPinkPrimary)
                    }
                    HorizontalDivider(color = LaundryMateLine)
                }
            }
        }

        // Recent Transactions
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Recent Transactions", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = LaundryTextPrimary)

                orders.take(5).forEach { order ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Order #${order.id} • ${order.customerName}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = LaundryTextPrimary)
                            Text("${order.date} • ${order.status}", fontSize = 11.sp, color = LaundryTextMuted)
                        }
                        Text("₱${order.totalAmount.toInt()}.00", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = LaundryTextPrimary)
                    }
                    HorizontalDivider(color = LaundryMateLine)
                }
            }
        }
    }
}
