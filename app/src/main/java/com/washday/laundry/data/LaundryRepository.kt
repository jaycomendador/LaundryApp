package com.washday.laundry.data

import com.washday.laundry.model.AppNotification
import com.washday.laundry.model.DailySalesSummary
import com.washday.laundry.model.LaundryOrder
import com.washday.laundry.model.LaundryService
import com.washday.laundry.model.ServiceSalesSplit
import com.washday.laundry.model.TransactionItem
import com.washday.laundry.model.WeeklySalesPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LaundryRepository {

    val services = listOf(
        LaundryService("1", "Wash & Fold", "Fresh and ready", 65, "sparkles"),
        LaundryService("2", "Wash & Dry", "Complete care", 75, "wind"),
        LaundryService("3", "Dry Cleaning", "Delicate care", 120, "bag"),
        LaundryService("4", "Ironing", "Pressed perfectly", 45, "iron"),
        LaundryService("5", "Self-Service", "Use our machines", 55, "washer")
    )

    private val _orders = MutableStateFlow(
        listOf(
            LaundryOrder(
                id = "1024",
                customerName = "Lena Morales",
                contactNumber = "+63 912 345 6789",
                serviceName = "Wash & Fold",
                date = "June 18, 2025",
                totalAmount = 325.0,
                weightKg = 5,
                status = "Washing",
                paymentStatus = "Paid",
                time = "Today, 4:30 PM"
            ),
            LaundryOrder(
                id = "1023",
                customerName = "Juan Dela Cruz",
                contactNumber = "+63 917 555 1234",
                serviceName = "Dry Cleaning",
                date = "June 18, 2025",
                totalAmount = 480.0,
                weightKg = 4,
                status = "Ready for Pickup",
                paymentStatus = "Paid",
                time = "Today, 3:00 PM"
            ),
            LaundryOrder(
                id = "1022",
                customerName = "Sophia Reyes",
                contactNumber = "+63 918 888 9999",
                serviceName = "Wash & Dry",
                date = "June 18, 2025",
                totalAmount = 300.0,
                weightKg = 4,
                status = "Received",
                paymentStatus = "Pending",
                time = "Today, 11:15 AM"
            ),
            LaundryOrder(
                id = "1021",
                customerName = "Mark Santos",
                contactNumber = "+63 922 111 2233",
                serviceName = "Ironing",
                date = "June 18, 2025",
                totalAmount = 225.0,
                weightKg = 5,
                status = "Ready for Pickup",
                paymentStatus = "Paid",
                time = "Today, 10:00 AM"
            ),
            LaundryOrder(
                id = "1020",
                customerName = "Angela Perez",
                contactNumber = "+63 920 444 5555",
                serviceName = "Wash & Fold",
                date = "June 17, 2025",
                totalAmount = 390.0,
                weightKg = 6,
                status = "Completed",
                paymentStatus = "Paid",
                time = "Yesterday"
            )
        )
    )
    val orders: StateFlow<List<LaundryOrder>> = _orders.asStateFlow()

    private val _transactions = MutableStateFlow(
        listOf(
            TransactionItem("#1024", "Lena Morales", "June 18", "₱325.00", "Paid"),
            TransactionItem("#1023", "Juan Dela Cruz", "June 18", "₱480.00", "Paid"),
            TransactionItem("#1022", "Sophia Reyes", "June 18", "₱300.00", "Pending"),
            TransactionItem("#1021", "Mark Santos", "June 18", "₱225.00", "Paid")
        )
    )
    val transactions: StateFlow<List<TransactionItem>> = _transactions.asStateFlow()

    private val _notifications = MutableStateFlow(
        listOf(
            AppNotification("1", "3 orders need attention", "3 orders in Received status waiting for washing.", "10m ago", false),
            AppNotification("2", "5 orders ready for pickup", "Notify customers that their laundry is ready.", "1h ago", false)
        )
    )
    val notifications: StateFlow<List<AppNotification>> = _notifications.asStateFlow()

    val dailySalesSummary = DailySalesSummary(
        totalRevenueToday = 8460.0,
        ordersNeedingAttention = 3,
        ordersReadyForPickup = 5,
        completedOrdersToday = 12
    )

    val weeklySalesPoints = listOf(
        WeeklySalesPoint("Mon", 4200.0),
        WeeklySalesPoint("Tue", 5800.0),
        WeeklySalesPoint("Wed", 6100.0),
        WeeklySalesPoint("Thu", 7400.0),
        WeeklySalesPoint("Fri", 9200.0),
        WeeklySalesPoint("Sat", 10500.0),
        WeeklySalesPoint("Sun", 8460.0)
    )

    val serviceSalesSplit = listOf(
        ServiceSalesSplit("Wash & Fold", 45, 3807.0),
        ServiceSalesSplit("Wash & Dry", 35, 2961.0),
        ServiceSalesSplit("Dry Cleaning", 20, 1692.0)
    )

    fun createNewOrder(customerName: String, contactNumber: String, service: LaundryService, weightKg: Int): LaundryOrder {
        val nextId = (1025..1099).random().toString()
        val total = (service.pricePerKg * weightKg).toDouble()

        val newOrder = LaundryOrder(
            id = nextId,
            customerName = customerName,
            contactNumber = contactNumber,
            serviceName = service.name,
            date = "Today",
            totalAmount = total,
            weightKg = weightKg,
            status = "Received",
            paymentStatus = "Paid",
            time = "Just now"
        )

        _orders.update { current ->
            listOf(newOrder) + current
        }

        return newOrder
    }

    fun updateOrderStatus(orderId: String, newStatus: String) {
        _orders.update { list ->
            list.map { order ->
                if (order.id == orderId) {
                    order.copy(status = newStatus)
                } else order
            }
        }
    }

    fun markNotificationsRead() {
        _notifications.update { list ->
            list.map { it.copy(isRead = true) }
        }
    }
}
