package com.washday.laundry.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.washday.laundry.model.LaundryService

@Entity(tableName = "services")
data class ServiceEntity(
    @PrimaryKey val id: String,
    val name: String,
    val detail: String?,
    val pricePerKg: Double,
    val iconName: String
)

fun ServiceEntity.toDomainModel(): LaundryService {
    return LaundryService(
        id = id,
        name = name,
        detail = detail,
        pricePerKg = pricePerKg,
        iconName = iconName
    )
}

fun LaundryService.toEntity(): ServiceEntity {
    return ServiceEntity(
        id = id,
        name = name,
        detail = detail,
        pricePerKg = pricePerKg,
        iconName = iconName
    )
}
