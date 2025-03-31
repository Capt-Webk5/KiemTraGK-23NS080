package com.example.ktgk_23ns102.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.ktgk_23ns102.model.data.Order
import com.example.ktgk_23ns102.model.repository.OrderRepository
import kotlinx.coroutines.launch

class OrderViewModel (private val repository: OrderRepository) : ViewModel() {

    val allOrders = repository.allOrders.asLiveData()

    fun addOrder(customerName: String, phoneNumber: String, totalPrice: Double, status: String) = viewModelScope.launch {
        repository.insertOrder(customerName, phoneNumber, totalPrice, status)
    }

    fun updateOrder(order: Order) = viewModelScope.launch {
        repository.updateOrder(order)
    }

    fun deleteOrderById(orderId: Int) = viewModelScope.launch {
        repository.deleteOrderById(orderId)
    }
}

class OrderViewModelFactory(private val repository: OrderRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OrderViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}