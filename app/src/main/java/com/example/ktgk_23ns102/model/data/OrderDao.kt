package com.example.ktgk_23ns102.model.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Insert
    suspend fun insertOrder(order: Order): Long

    @Query("SELECT * FROM orders")
    fun getAllOrders(): Flow<List<Order>>

    @Query("SELECT * FROM orders WHERE id = :orderId")
    suspend fun getOrderById(orderId: Int): Order?

    @Update
    suspend fun updateOrder(order: Order)

    @Delete
    suspend fun deleteOrder(order: Order)

    @Query("DELETE FROM orders WHERE id = :orderId")
    suspend fun deleteOrderById(orderId: Int)
}