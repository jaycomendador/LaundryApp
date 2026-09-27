package com.washday.laundry.model

enum class ShopTab(val title: String, val iconName: String) {
    DASHBOARD("Dashboard", "home"),
    ORDERS("Orders", "receipt"),
    NEW_ORDER("New Order", "plus"),
    SALES("Sales Report", "chart"),
    SERVICES("Services", "washer")
}

data class LaundryService(
    val id: String,
    val name: String,
    val detail: String? = null,
    val pricePerKg: Int,
    val iconName: String
)

data class LaundryOrder(
    val id: String,
    val customerName: String,
    val contactNumber: String,
    val serviceName: String,
    val date: String,
    val totalAmount: Double,
    val weightKg: Int,
    val status: String, // Received, Washing, Drying, Ready for Pickup, Completed
    val paymentStatus: String = "Paid",
    val time: String = "Today, 4:30 PM"
)

data class AppNotification(
    val id: String,
    val title: String,
    val message: String,
    val time: String,
    val isRead: Boolean = false
)

data class DailySalesSummary(
    val totalRevenueToday: Double = 8460.0,
    val ordersNeedingAttention: Int = 3,
    val ordersReadyForPickup: Int = 5,
    val completedOrdersToday: Int = 12
)

data class WeeklySalesPoint(
    val day: String,
    val revenue: Double
)

data class ServiceSalesSplit(
    val serviceName: String,
    val percentage: Int,
    val amount: Double
)

data class TransactionItem(
    val orderId: String,
    val customerName: String,
    val date: String,
    val amount: String,
    val status: String
)
