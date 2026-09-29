package com.washday.laundry.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.washday.laundry.model.AppNotification

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey val id: String,
    val title: String,
    val message: String,
    val time: String,
    val isRead: Boolean = false
)

fun NotificationEntity.toDomainModel(): AppNotification {
    return AppNotification(
        id = id,
        title = title,
        message = message,
        time = time,
        isRead = isRead
    )
}

fun AppNotification.toEntity(): NotificationEntity {
    return NotificationEntity(
        id = id,
        title = title,
        message = message,
        time = time,
        isRead = isRead
    )
}
