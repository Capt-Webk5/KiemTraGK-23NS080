package com.example.ktgk_23ns102.model.data

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    val email: String,
    val avatar: String
)

data class UserResponse(
    val data: List<User>
)