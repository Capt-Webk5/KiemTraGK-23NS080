package com.example.ktgk_23ns102.model.network

import com.example.ktgk_23ns102.model.data.UserResponse
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): UserResponse
}

object RetrofitInstance {
    private const val BASE_URL = "https://reqres.in/api/"

    val api: ApiService by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}