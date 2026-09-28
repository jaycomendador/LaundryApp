package com.washday.laundry.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceDao {
    @Query("SELECT * FROM services")
    fun getAllServicesFlow(): Flow<List<ServiceEntity>>

    @Query("SELECT * FROM services")
    suspend fun getAllServices(): List<ServiceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertServices(services: List<ServiceEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertService(service: ServiceEntity)
}
