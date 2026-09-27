package com.washday.laundry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.washday.laundry.data.LaundryRepository
import com.washday.laundry.model.ShopTab
import com.washday.laundry.ui.components.ShopBottomNavigation
import com.washday.laundry.ui.components.ShopHeader
import com.washday.laundry.ui.components.ShopSidebarNavigation
import com.washday.laundry.ui.components.ToastNotification
import com.washday.laundry.ui.screens.NewOrderScreen
import com.washday.laundry.ui.screens.SalesReportScreen
import com.washday.laundry.ui.screens.ServicesCatalogScreen
import com.washday.laundry.ui.screens.ShopDashboardScreen
import com.washday.laundry.ui.screens.ShopOrdersScreen
import com.washday.laundry.ui.theme.WashdayCanvas
import com.washday.laundry.ui.theme.WashdayTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val repository = LaundryRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WashdayTheme {
                WashdayShopApp(repository = repository)
            }
        }
    }
}

@Composable
fun WashdayShopApp(repository: LaundryRepository) {
    var currentTab by remember { mutableStateOf(ShopTab.DASHBOARD) }
    var toastMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    val orders by repository.orders.collectAsState()
    val transactions by repository.transactions.collectAsState()
    val notifications by repository.notifications.collectAsState()

    fun showToast(msg: String) {
        toastMessage = msg
        scope.launch {
            delay(2600)
            if (toastMessage == msg) {
                toastMessage = null
            }
        }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isTabletOrDesktop = maxWidth >= 600.dp

        if (isTabletOrDesktop) {
            // Wide screen sidebar layout
            Row(modifier = Modifier.fillMaxSize().background(WashdayCanvas)) {
                ShopSidebarNavigation(
                    selectedTab = currentTab,
                    onTabSelected = { currentTab = it },
                    onSignOut = { showToast("You have been signed out safely") }
                )

                Column(modifier = Modifier.weight(1f).fillMaxSize()) {
                    ShopHeader(
                        notifications = notifications,
                        onMarkNotificationsRead = {
                            repository.markNotificationsRead()
                            showToast("All notifications marked as read")
                        }
                    )

                    Box(modifier = Modifier.weight(1f).fillMaxSize()) {
                        when (currentTab) {
                            ShopTab.DASHBOARD -> ShopDashboardScreen(
                                orders = orders,
                                dailySalesSummary = repository.dailySalesSummary,
                                onNavigateToTab = { currentTab = it },
                                onUpdateOrderStatus = { id, st -> repository.updateOrderStatus(id, st) },
                                onShowToast = { showToast(it) }
                            )
                            ShopTab.ORDERS -> ShopOrdersScreen(
                                orders = orders,
                                onUpdateOrderStatus = { id, st -> repository.updateOrderStatus(id, st) },
                                onShowToast = { showToast(it) }
                            )
                            ShopTab.NEW_ORDER -> NewOrderScreen(
                                services = repository.services,
                                onSaveOrder = { name, contact, service, weight ->
                                    repository.createNewOrder(name, contact, service, weight)
                                    currentTab = ShopTab.ORDERS
                                },
                                onShowToast = { showToast(it) }
                            )
                            ShopTab.SALES -> SalesReportScreen(
                                dailySalesSummary = repository.dailySalesSummary,
                                weeklySalesPoints = repository.weeklySalesPoints,
                                serviceSalesSplit = repository.serviceSalesSplit,
                                transactions = transactions
                            )
                            ShopTab.SERVICES -> ServicesCatalogScreen(
                                services = repository.services
                            )
                        }

                        ToastNotification(
                            message = toastMessage,
                            modifier = Modifier.align(Alignment.TopCenter)
                        )
                    }
                }
            }
        } else {
            // Mobile layout with bottom navigation
            Scaffold(
                topBar = {
                    ShopHeader(
                        notifications = notifications,
                        onMarkNotificationsRead = {
                            repository.markNotificationsRead()
                            showToast("All notifications marked as read")
                        }
                    )
                },
                bottomBar = {
                    ShopBottomNavigation(
                        selectedTab = currentTab,
                        onTabSelected = { currentTab = it }
                    )
                },
                containerColor = WashdayCanvas
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    when (currentTab) {
                        ShopTab.DASHBOARD -> ShopDashboardScreen(
                            orders = orders,
                            dailySalesSummary = repository.dailySalesSummary,
                            onNavigateToTab = { currentTab = it },
                            onUpdateOrderStatus = { id, st -> repository.updateOrderStatus(id, st) },
                            onShowToast = { showToast(it) }
                        )
                        ShopTab.ORDERS -> ShopOrdersScreen(
                            orders = orders,
                            onUpdateOrderStatus = { id, st -> repository.updateOrderStatus(id, st) },
                            onShowToast = { showToast(it) }
                        )
                        ShopTab.NEW_ORDER -> NewOrderScreen(
                            services = repository.services,
                            onSaveOrder = { name, contact, service, weight ->
                                repository.createNewOrder(name, contact, service, weight)
                                currentTab = ShopTab.ORDERS
                            },
                            onShowToast = { showToast(it) }
                        )
                        ShopTab.SALES -> SalesReportScreen(
                            dailySalesSummary = repository.dailySalesSummary,
                            weeklySalesPoints = repository.weeklySalesPoints,
                            serviceSalesSplit = repository.serviceSalesSplit,
                            transactions = transactions
                        )
                        ShopTab.SERVICES -> ServicesCatalogScreen(
                            services = repository.services
                        )
                    }

                    ToastNotification(
                        message = toastMessage,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }
            }
        }
    }
}
