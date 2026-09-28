package com.washday.laundry.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders ORDER BY id DESC")
    fun getAllOrdersFlow(): Flow<List<OrderEntity>>

    @Query("SELECT * FROM orders ORDER BY id DESC")
    suspend fun getAllOrders(): List<OrderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrders(orders: List<OrderEntity>)

    @Query("UPDATE orders SET status = :newStatus WHERE id = :orderId")
    suspend fun updateOrderStatus(orderId: String, newStatus: String)

    @Query("DELETE FROM orders WHERE id = :orderId")
    suspend fun deleteOrder(orderId: String)

    @Query("DELETE FROM orders")
    suspend fun deleteAllOrders()
}
