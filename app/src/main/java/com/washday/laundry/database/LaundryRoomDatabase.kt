package com.washday.laundry.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [OrderEntity::class, ServiceEntity::class, NotificationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LaundryRoomDatabase : RoomDatabase() {

    abstract fun orderDao(): OrderDao
    abstract fun serviceDao(): ServiceDao
    abstract fun notificationDao(): NotificationDao

    companion object {
        @Volatile
        private var INSTANCE: LaundryRoomDatabase? = null

        fun getDatabase(context: Context): LaundryRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LaundryRoomDatabase::class.java,
                    "laundry_mate_sqlite.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
