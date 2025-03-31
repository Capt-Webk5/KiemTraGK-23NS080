package com.example.ktgk_23ns102

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.ktgk_23ns102.model.data.OrderDatabase
import com.example.ktgk_23ns102.model.repository.OrderRepository
import com.example.ktgk_23ns102.ui.OrderScreen
import com.example.ktgk_23ns102.ui.UserScreen
import com.example.ktgk_23ns102.ui.theme.KTGK_23NS102Theme
import com.example.ktgk_23ns102.viewmodel.OrderViewModel
import com.example.ktgk_23ns102.viewmodel.OrderViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val database = OrderDatabase.getDatabase(this)
        val repository = OrderRepository(database.orderDao())
        val orderViewModel = ViewModelProvider(this, OrderViewModelFactory(repository)).get(OrderViewModel::class.java)

        setContent {
            KTGK_23NS102Theme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OrderScreen(viewModel = orderViewModel)
                    //UserScreen()
                }
            }
        }
    }
}