package com.example.ktgk_23ns102.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val customer_name: String,
    val phone_number: String,
    val total_price: Double,
    val status: String
)