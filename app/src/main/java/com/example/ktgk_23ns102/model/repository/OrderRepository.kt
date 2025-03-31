package com.example.ktgk_23ns102.model.repository

import android.util.Patterns
import com.example.ktgk_23ns102.model.data.Order
import com.example.ktgk_23ns102.model.data.OrderDao
import kotlinx.coroutines.flow.Flow

class OrderRepository(private val orderDao: OrderDao) {
    val allOrders: Flow<List<Order>> = orderDao.getAllOrders()

    private fun validateOrder(customerName: String, phoneNumber: String, totalPrice: Double): Boolean {
        if (customerName.isBlank())
            return false
        if (!Patterns.PHONE.matcher(phoneNumber).matches())
            return false
        if (totalPrice < 0)
            return false
        return true
    }

    suspend fun insertOrder(customerName: String, phoneNumber: String, totalPrice: Double, status: String): Result<Long> {
       return if (validateOrder(customerName, phoneNumber, totalPrice)) {
           try {
               val newOrder = Order(
                   customer_name = customerName,
                   phone_number = phoneNumber,
                   total_price = totalPrice,
                   status = status
               )
               val id = orderDao.insertOrder(newOrder)
                Result.success(id)
           } catch (e: Exception) {
               Result.failure(e)
           }
       } else {
           Result.failure(IllegalArgumentException("Dữ liệu không hợp lệ"))
       }
    }

    suspend fun updateOrder(order: Order): Result<Unit> {
        return if (validateOrder(order.customer_name, order.phone_number, order.total_price)) {
            try {
                orderDao.updateOrder(order)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            Result.failure(IllegalArgumentException("Dữ liệu không hợp lệ"))
        }
    }

    suspend fun deleteOrderById(orderId: Int): Result<Unit> {
        return try {
            orderDao.deleteOrderById(orderId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}