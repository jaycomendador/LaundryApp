package com.washday.laundry.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.washday.laundry.model.LaundryOrder

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey val id: String,
    val customerName: String,
    val contactNumber: String,
    val serviceName: String,
    val date: String,
    val time: String,
    val weightKg: Double,
    val pricePerKg: Double,
    val basePrice: Double,
    val additionalCharges: Double,
    val totalAmount: Double,
    val status: String,
    val paymentStatus: String = "Paid"
)

fun OrderEntity.toDomainModel(): LaundryOrder {
    return LaundryOrder(
        id = id,
        customerName = customerName,
        contactNumber = contactNumber,
        serviceName = serviceName,
        date = date,
        time = time,
        weightKg = weightKg,
        pricePerKg = pricePerKg,
        basePrice = basePrice,
        additionalCharges = additionalCharges,
        totalAmount = totalAmount,
        status = status,
        paymentStatus = paymentStatus
    )
}

fun LaundryOrder.toEntity(): OrderEntity {
    return OrderEntity(
        id = id,
        customerName = customerName,
        contactNumber = contactNumber,
        serviceName = serviceName,
        date = date,
        time = time,
        weightKg = weightKg,
        pricePerKg = pricePerKg,
        basePrice = basePrice,
        additionalCharges = additionalCharges,
        totalAmount = totalAmount,
        status = status,
        paymentStatus = paymentStatus
    )
}
