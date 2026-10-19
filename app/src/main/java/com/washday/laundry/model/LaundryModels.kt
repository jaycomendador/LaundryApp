package com.washday.laundry.model

enum class ShopTab(val title: String, val iconName: String) {
    DASHBOARD("Home", "home"),
    ORDERS("Orders", "receipt"),
    NEW_ORDER("New Order", "plus"),
    COMPLETED_HISTORY("History", "history"),
    SETTINGS("Settings", "gear"),
    // Additional secondary pages accessed via Dashboard & Settings
    SALES("Sales Summary", "chart"),
    CUSTOMERS("Customers", "user"),
    SERVICES("Services", "washer")
}

data class UserSession(
    val username: String,
    val name: String,
    val role: String = "Admin / Staff"
)

data class LaundryService(
    val id: String,
    val name: String,
    val detail: String? = null,
    val pricePerKg: Double,
    val iconName: String
)

data class LaundryOrder(
    val id: String,
    val customerName: String,
    val contactNumber: String,
    val serviceName: String,
    val date: String,
    val time: String,
    val weightKg: Double,
    val pricePerKg: Double,
    val basePrice: Double,
    val additionalCharges: Double = 0.0,
    val totalAmount: Double,
    val status: String, // Received, Washing, Drying, Ready for Pickup, Claimed, Completed
    val paymentStatus: String = "Paid"
)

data class CustomerRecord(
    val id: String,
    val name: String,
    val contactNumber: String,
    val totalOrders: Int,
    val totalSpent: Double,
    val lastOrderDate: String
)

data class AppNotification(
    val id: String,
    val title: String,
    val message: String,
    val time: String,
    val isRead: Boolean = false
)

data class DailySalesSummary(
    val totalRevenueToday: Double,
    val totalOrdersCount: Int,
    val inProgressCount: Int,
    val completedCount: Int
)

data class WeeklySalesPoint(
    val day: String,
    val revenue: Double
)

data class ServiceSalesSplit(
    val serviceName: String,
    val amount: Double,
    val percentage: Int
)
