package com.example.ktgk_23ns102.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktgk_23ns102.model.data.User
import com.example.ktgk_23ns102.model.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val repository = UserRepository()
    val userList = mutableStateOf<List<User>>(emptyList())

    fun fetchUsers() {
        viewModelScope.launch {
            userList.value = repository.getUsers()
        }
    }
}