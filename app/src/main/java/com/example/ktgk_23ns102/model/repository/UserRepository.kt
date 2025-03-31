package com.example.ktgk_23ns102.model.repository

import com.example.ktgk_23ns102.model.data.User
import com.example.ktgk_23ns102.model.network.RetrofitInstance

class UserRepository {
    suspend fun getUsers(): List<User> {
        return RetrofitInstance.api.getUsers().data
    }
}