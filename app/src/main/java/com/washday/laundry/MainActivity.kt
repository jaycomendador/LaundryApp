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
import com.washday.laundry.model.LaundryOrder
import com.washday.laundry.model.ShopTab
import com.washday.laundry.ui.components.ShopBottomNavigation
import com.washday.laundry.ui.components.ShopHeader
import com.washday.laundry.ui.components.ShopSidebarNavigation
import com.washday.laundry.ui.components.ToastNotification
import com.washday.laundry.ui.screens.CustomerHistoryScreen
import com.washday.laundry.ui.screens.DashboardScreen
import com.washday.laundry.ui.screens.LoginScreen
import com.washday.laundry.ui.screens.NewOrderFlowScreen
import com.washday.laundry.ui.screens.OrderDetailsScreen
import com.washday.laundry.ui.screens.SalesReportScreen
import com.washday.laundry.ui.screens.ServicesCatalogScreen
import com.washday.laundry.ui.screens.SettingsScreen
import com.washday.laundry.ui.screens.ShopOrdersScreen
import com.washday.laundry.ui.screens.SplashScreen
import com.washday.laundry.ui.theme.LaundryMateCanvas
import com.washday.laundry.ui.theme.LaundryMateTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class MainActivity : ComponentActivity() {

    private lateinit var repository: LaundryRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = LaundryRepository(this)

        enableEdgeToEdge()
        setContent {
            LaundryMateTheme {
                LaundryMateApp(repository = repository)
            }
        }
    }
}

@Composable
fun LaundryMateApp(repository: LaundryRepository) {
    var isSplashActive by remember { mutableStateOf(true) }
    var currentTab by remember { mutableStateOf(ShopTab.DASHBOARD) }
    var selectedOrderForDetail by remember { mutableStateOf<LaundryOrder?>(null) }
    var toastMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    val currentUser = repository.currentUser.collectAsState().value
    val services = repository.services.collectAsState().value
    val orders = repository.orders.collectAsState().value
    val notifications = repository.notifications.collectAsState().value

    fun showToast(msg: String) {
        toastMessage = msg
        scope.launch {
            delay(2600.milliseconds)
            if (toastMessage == msg) {
                toastMessage = null
            }
        }
    }

    if (isSplashActive) {
        SplashScreen(onSplashFinished = { isSplashActive = false })
    } else if (currentUser == null) {
        LoginScreen(
            onLoginSubmitted = { username, password ->
                repository.login(username, password)
            },
            onShowToast = { showToast(it) }
        )
    } else {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val isTabletOrDesktop = this.maxWidth >= 600.dp

            if (isTabletOrDesktop) {
                Row(modifier = Modifier.fillMaxSize().background(LaundryMateCanvas)) {
                    ShopSidebarNavigation(
                        selectedTab = currentTab,
                        onTabSelected = {
                            selectedOrderForDetail = null
                            currentTab = it
                        },
                        onSignOut = {
                            repository.logout()
                            showToast("Logged out safely")
                        }
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
                            if (selectedOrderForDetail != null) {
                                OrderDetailsScreen(
                                    order = selectedOrderForDetail!!,
                                    onUpdateStatus = { id, st ->
                                        repository.updateOrderStatus(id, st)
                                        selectedOrderForDetail = selectedOrderForDetail?.copy(status = st)
                                    },
                                    onBack = { selectedOrderForDetail = null },
                                    onShowToast = { showToast(it) }
                                )
                            } else {
                                when (currentTab) {
                                    ShopTab.DASHBOARD -> DashboardScreen(
                                        orders = orders,
                                        dailySalesSummary = repository.getDailySalesSummary(),
                                        onNavigateToTab = { currentTab = it },
                                        onUpdateOrderStatus = { id, st -> repository.updateOrderStatus(id, st) },
                                        onShowToast = { showToast(it) }
                                    )
                                    ShopTab.ORDERS -> ShopOrdersScreen(
                                        orders = orders,
                                        onUpdateOrderStatus = { id, st -> repository.updateOrderStatus(id, st) },
                                        onShowToast = { showToast(it) }
                                    )
                                    ShopTab.NEW_ORDER -> NewOrderFlowScreen(
                                        services = services,
                                        onSaveOrder = { name, contact, service, weight, charges ->
                                            repository.saveNewOrder(name, contact, service, weight, charges)
                                            currentTab = ShopTab.ORDERS
                                        },
                                        onShowToast = { showToast(it) }
                                    )
                                    ShopTab.SALES -> SalesReportScreen(
                                        dailySalesSummary = repository.getDailySalesSummary(),
                                        weeklySalesPoints = repository.getWeeklySalesPoints(),
                                        serviceSalesSplit = repository.getServiceSalesSplit(),
                                        orders = orders
                                    )
                                    ShopTab.CUSTOMERS -> CustomerHistoryScreen(
                                        customers = repository.getCustomerRecords()
                                    )
                                    ShopTab.SERVICES -> ServicesCatalogScreen(
                                        services = services
                                    )
                                    ShopTab.SETTINGS -> SettingsScreen(
                                        onExportBackup = { repository.exportBackupJson() },
                                        onImportRestore = { repository.importRestoreJson(it) },
                                        onLogout = { repository.logout() },
                                        onShowToast = { showToast(it) }
                                    )
                                }
                            }

                            ToastNotification(
                                message = toastMessage,
                                modifier = Modifier.align(Alignment.TopCenter)
                            )
                        }
                    }
                }
            } else {
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
                            onTabSelected = {
                                selectedOrderForDetail = null
                                currentTab = it
                            }
                        )
                    },
                    containerColor = LaundryMateCanvas
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        if (selectedOrderForDetail != null) {
                            OrderDetailsScreen(
                                order = selectedOrderForDetail!!,
                                onUpdateStatus = { id, st ->
                                    repository.updateOrderStatus(id, st)
                                    selectedOrderForDetail = selectedOrderForDetail?.copy(status = st)
                                },
                                onBack = { selectedOrderForDetail = null },
                                onShowToast = { showToast(it) }
                            )
                        } else {
                            when (currentTab) {
                                ShopTab.DASHBOARD -> DashboardScreen(
                                    orders = orders,
                                    dailySalesSummary = repository.getDailySalesSummary(),
                                    onNavigateToTab = { currentTab = it },
                                    onUpdateOrderStatus = { id, st -> repository.updateOrderStatus(id, st) },
                                    onShowToast = { showToast(it) }
                                )
                                ShopTab.ORDERS -> ShopOrdersScreen(
                                    orders = orders,
                                    onUpdateOrderStatus = { id, st -> repository.updateOrderStatus(id, st) },
                                    onShowToast = { showToast(it) }
                                )
                                ShopTab.NEW_ORDER -> NewOrderFlowScreen(
                                    services = services,
                                    onSaveOrder = { name, contact, service, weight, charges ->
                                        repository.saveNewOrder(name, contact, service, weight, charges)
                                        currentTab = ShopTab.ORDERS
                                    },
                                    onShowToast = { showToast(it) }
                                )
                                ShopTab.SALES -> SalesReportScreen(
                                    dailySalesSummary = repository.getDailySalesSummary(),
                                    weeklySalesPoints = repository.getWeeklySalesPoints(),
                                    serviceSalesSplit = repository.getServiceSalesSplit(),
                                    orders = orders
                                )
                                ShopTab.CUSTOMERS -> CustomerHistoryScreen(
                                    customers = repository.getCustomerRecords()
                                )
                                ShopTab.SERVICES -> ServicesCatalogScreen(
                                    services = services
                                )
                                ShopTab.SETTINGS -> SettingsScreen(
                                    onExportBackup = { repository.exportBackupJson() },
                                    onImportRestore = { repository.importRestoreJson(it) },
                                    onLogout = { repository.logout() },
                                    onShowToast = { showToast(it) }
                                )
                            }
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
}
