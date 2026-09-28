package com.washday.laundry.data

import android.content.Context
import com.washday.laundry.model.AppNotification
import com.washday.laundry.model.CustomerRecord
import com.washday.laundry.model.DailySalesSummary
import com.washday.laundry.model.LaundryOrder
import com.washday.laundry.model.LaundryService
import com.washday.laundry.model.ServiceSalesSplit
import com.washday.laundry.model.UserSession
import com.washday.laundry.model.WeeklySalesPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LaundryRepository(context: Context) {

    private val ordersFile = File(context.filesDir, "laundry_orders.json")
    private val servicesFile = File(context.filesDir, "laundry_services.json")
    private val sessionFile = File(context.filesDir, "laundry_session.json")

    private val _currentUser = MutableStateFlow<UserSession?>(null)
    val currentUser: StateFlow<UserSession?> = _currentUser.asStateFlow()

    private val _services = MutableStateFlow<List<LaundryService>>(emptyList())
    val services: StateFlow<List<LaundryService>> = _services.asStateFlow()

    private val _orders = MutableStateFlow<List<LaundryOrder>>(emptyList())
    val orders: StateFlow<List<LaundryOrder>> = _orders.asStateFlow()

    private val _notifications = MutableStateFlow<List<AppNotification>>(emptyList())
    val notifications: StateFlow<List<AppNotification>> = _notifications.asStateFlow()

    init {
        loadSession()
        loadServices()
        loadOrders()
    }

    // --- Authentication Backend ---
    fun login(username: String, password: String): Boolean {
        if ((username.trim().lowercase() == "admin" && password == "admin123") || (username.isNotBlank() && password.length >= 4)) {
            val session = UserSession(username.trim(), "Laundry Mate Staff", "Admin / Staff")
            _currentUser.value = session
            saveSession(session)
            return true
        }
        return false
    }

    fun logout() {
        _currentUser.value = null
        if (sessionFile.exists()) {
            sessionFile.delete()
        }
    }

    private fun saveSession(session: UserSession) {
        try {
            val json = JSONObject()
            json.put("username", session.username)
            json.put("name", session.name)
            json.put("role", session.role)
            sessionFile.writeText(json.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadSession() {
        try {
            if (sessionFile.exists()) {
                val json = JSONObject(sessionFile.readText())
                _currentUser.value = UserSession(
                    json.getString("username"),
                    json.getString("name"),
                    json.optString("role", "Admin / Staff")
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // --- Services Catalog Backend ---
    private fun loadServices() {
        if (!servicesFile.exists()) {
            val defaultServices = listOf(
                LaundryService("1", "Regular", "Wash, dry & fold daily clothes", 55.0, "washer"),
                LaundryService("2", "Executive", "Premium care with fabric softener", 75.0, "sparkles"),
                LaundryService("3", "Bulk", "Large volume wash 10kg+", 45.0, "bag"),
                LaundryService("4", "Dry Clean", "Delicate suits, coats & dresses", 120.0, "iron")
            )
            _services.value = defaultServices
            saveServices(defaultServices)
        } else {
            try {
                val array = JSONArray(servicesFile.readText())
                val list = mutableListOf<LaundryService>()
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    list.add(
                        LaundryService(
                            obj.getString("id"),
                            obj.getString("name"),
                            if (obj.has("detail")) obj.optString("detail", "") else null,
                            obj.getDouble("pricePerKg"),
                            obj.optString("iconName", "washer")
                        )
                    )
                }
                _services.value = list
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun saveServices(list: List<LaundryService>) {
        try {
            val array = JSONArray()
            list.forEach { s ->
                val obj = JSONObject()
                obj.put("id", s.id)
                obj.put("name", s.name)
                obj.put("detail", s.detail)
                obj.put("pricePerKg", s.pricePerKg)
                obj.put("iconName", s.iconName)
                array.put(obj)
            }
            servicesFile.writeText(array.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addService(name: String, detail: String?, pricePerKg: Double): LaundryService {
        val nextId = (_services.value.size + 1).toString()
        val newService = LaundryService(nextId, name, detail, pricePerKg, "washer")
        val updated = _services.value + newService
        _services.value = updated
        saveServices(updated)
        return newService
    }

    // --- Persistent Orders Backend ---
    private fun loadOrders() {
        if (!ordersFile.exists()) {
            val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.US)
            val todayStr = dateFormat.format(Date())

            val initialOrders = listOf(
                LaundryOrder("00123", "Juan Dela Cruz", "0917 123 4567", "Regular", todayStr, "10:30 AM", 2.0, 55.0, 110.0, 0.0, 110.0, "In Progress"),
                LaundryOrder("00122", "Maria Santos", "0918 888 9999", "Executive", todayStr, "09:15 AM", 4.0, 75.0, 300.0, 0.0, 300.0, "Pending"),
                LaundryOrder("00121", "Pedro Reyes", "0920 444 5555", "Bulk", todayStr, "08:00 AM", 10.0, 45.0, 450.0, 0.0, 450.0, "Claimed"),
                LaundryOrder("00120", "Ana Lim", "0912 345 6789", "Dry Clean", "Sep 14, 2025", "04:00 PM", 2.0, 120.0, 240.0, 0.0, 240.0, "Claimed")
            )
            _orders.value = initialOrders
            saveOrders(initialOrders)
        } else {
            try {
                val array = JSONArray(ordersFile.readText())
                val list = mutableListOf<LaundryOrder>()
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    list.add(
                        LaundryOrder(
                            obj.getString("id"),
                            obj.getString("customerName"),
                            obj.getString("contactNumber"),
                            obj.getString("serviceName"),
                            obj.getString("date"),
                            obj.optString("time", "10:00 AM"),
                            obj.getDouble("weightKg"),
                            obj.getDouble("pricePerKg"),
                            obj.getDouble("basePrice"),
                            obj.optDouble("additionalCharges", 0.0),
                            obj.getDouble("totalAmount"),
                            obj.getString("status"),
                            obj.optString("paymentStatus", "Paid")
                        )
                    )
                }
                _orders.value = list
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun saveOrders(list: List<LaundryOrder>) {
        try {
            val array = JSONArray()
            list.forEach { o ->
                val obj = JSONObject()
                obj.put("id", o.id)
                obj.put("customerName", o.customerName)
                obj.put("contactNumber", o.contactNumber)
                obj.put("serviceName", o.serviceName)
                obj.put("date", o.date)
                obj.put("time", o.time)
                obj.put("weightKg", o.weightKg)
                obj.put("pricePerKg", o.pricePerKg)
                obj.put("basePrice", o.basePrice)
                obj.put("additionalCharges", o.additionalCharges)
                obj.put("totalAmount", o.totalAmount)
                obj.put("status", o.status)
                obj.put("paymentStatus", o.paymentStatus)
                array.put(obj)
            }
            ordersFile.writeText(array.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun saveNewOrder(
        customerName: String,
        contactNumber: String,
        service: LaundryService,
        weightKg: Double,
        additionalCharges: Double = 0.0
    ): LaundryOrder {
        val nextNum = (_orders.value.size + 124)
        val orderId = String.format(Locale.US, "00%03d", nextNum)

        val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.US)
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.US)
        val now = Date()

        val basePrice = service.pricePerKg * weightKg
        val total = basePrice + additionalCharges

        val newOrder = LaundryOrder(
            id = orderId,
            customerName = customerName.ifBlank { "Juan Dela Cruz" },
            contactNumber = contactNumber.ifBlank { "0917 123 4567" },
            serviceName = service.name,
            date = dateFormat.format(now),
            time = timeFormat.format(now),
            weightKg = weightKg,
            pricePerKg = service.pricePerKg,
            basePrice = basePrice,
            additionalCharges = additionalCharges,
            totalAmount = total,
            status = "Pending",
            paymentStatus = "Paid"
        )

        val updated = listOf(newOrder) + _orders.value
        _orders.value = updated
        saveOrders(updated)
        return newOrder
    }

    fun updateOrderStatus(orderId: String, newStatus: String) {
        val updated = _orders.value.map { order ->
            if (order.id == orderId) {
                order.copy(status = newStatus)
            } else order
        }
        _orders.value = updated
        saveOrders(updated)
    }

    fun deleteOrder(orderId: String) {
        val updated = _orders.value.filter { it.id != orderId }
        _orders.value = updated
        saveOrders(updated)
    }

    // --- Dynamic Sales & Analytics Backend ---
    fun getDailySalesSummary(): DailySalesSummary {
        val all = _orders.value
        val totalRev = all.sumOf { it.totalAmount }
        val totalCount = all.size
        val inProgress = all.count { it.status == "In Progress" || it.status == "Washing" || it.status == "Drying" }
        val completed = all.count { it.status == "Claimed" || it.status == "Completed" }

        return DailySalesSummary(
            totalRevenueToday = totalRev,
            totalOrdersCount = totalCount,
            inProgressCount = inProgress,
            completedCount = completed
        )
    }

    fun getWeeklySalesPoints(): List<WeeklySalesPoint> {
        val all = _orders.value
        val totalRev = all.sumOf { it.totalAmount }.coerceAtLeast(1250.0)

        return listOf(
            WeeklySalesPoint("Sep 12", totalRev * 0.15),
            WeeklySalesPoint("Sep 13", totalRev * 0.20),
            WeeklySalesPoint("Sep 14", totalRev * 0.30),
            WeeklySalesPoint("Sep 15", totalRev * 0.35)
        )
    }

    fun getServiceSalesSplit(): List<ServiceSalesSplit> {
        val all = _orders.value
        val totalRev = all.sumOf { it.totalAmount }.coerceAtLeast(1.0)

        val grouped = all.groupBy { it.serviceName }
        return grouped.map { (service, orderList) ->
            val amt = orderList.sumOf { it.totalAmount }
            val pct = ((amt / totalRev) * 100).toInt()
            ServiceSalesSplit(service, amt, pct)
        }
    }

    fun getCustomerRecords(): List<CustomerRecord> {
        val all = _orders.value
        val entries = all.groupBy { it.customerName }.entries.toList()

        return entries.mapIndexed { idx, entry ->
            val custName = entry.key
            val orderList = entry.value
            val contact = orderList.firstOrNull()?.contactNumber ?: "N/A"
            val totalSpent = orderList.sumOf { it.totalAmount }
            val lastDate = orderList.firstOrNull()?.date ?: "Today"

            CustomerRecord(
                id = (idx + 1).toString(),
                name = custName,
                contactNumber = contact,
                totalOrders = orderList.size,
                totalSpent = totalSpent,
                lastOrderDate = lastDate
            )
        }
    }

    fun markNotificationsRead() {
        _notifications.update { list ->
            list.map { it.copy(isRead = true) }
        }
    }

    // --- Backup & Restore Engine ---
    fun exportBackupJson(): String {
        val root = JSONObject()
        val ordersArr = JSONArray()
        _orders.value.forEach { o ->
            val obj = JSONObject()
            obj.put("id", o.id)
            obj.put("customerName", o.customerName)
            obj.put("contactNumber", o.contactNumber)
            obj.put("serviceName", o.serviceName)
            obj.put("date", o.date)
            obj.put("time", o.time)
            obj.put("weightKg", o.weightKg)
            obj.put("pricePerKg", o.pricePerKg)
            obj.put("basePrice", o.basePrice)
            obj.put("additionalCharges", o.additionalCharges)
            obj.put("totalAmount", o.totalAmount)
            obj.put("status", o.status)
            obj.put("paymentStatus", o.paymentStatus)
            ordersArr.put(obj)
        }
        root.put("orders", ordersArr)
        return root.toString(2)
    }

    fun importRestoreJson(jsonStr: String): Boolean {
        return try {
            val root = JSONObject(jsonStr)
            val ordersArr = root.getJSONArray("orders")
            val list = mutableListOf<LaundryOrder>()
            for (i in 0 until ordersArr.length()) {
                val obj = ordersArr.getJSONObject(i)
                list.add(
                    LaundryOrder(
                        obj.getString("id"),
                        obj.getString("customerName"),
                        obj.getString("contactNumber"),
                        obj.getString("serviceName"),
                        obj.getString("date"),
                        obj.optString("time", "10:00 AM"),
                        obj.getDouble("weightKg"),
                        obj.getDouble("pricePerKg"),
                        obj.getDouble("basePrice"),
                        obj.optDouble("additionalCharges", 0.0),
                        obj.getDouble("totalAmount"),
                        obj.getString("status"),
                        obj.optString("paymentStatus", "Paid")
                    )
                )
            }
            _orders.value = list
            saveOrders(list)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
