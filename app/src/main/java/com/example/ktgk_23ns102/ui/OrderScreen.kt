package com.example.ktgk_23ns102.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ktgk_23ns102.model.data.Order
import com.example.ktgk_23ns102.viewmodel.OrderViewModel
import kotlinx.coroutines.launch

@Composable
fun OrderScreen(viewModel: OrderViewModel) {
    var customerName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var totalPrice by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }

    var customerNameError by remember { mutableStateOf<String?>(null) }
    var phoneNumberError by remember { mutableStateOf<String?>(null) }
    var totalPriceError by remember { mutableStateOf<String?>(null) }
    var statusError by remember { mutableStateOf<String?>(null) }

    var selectedOrder by remember { mutableStateOf<Order?>(null) }
    val orders by viewModel.allOrders.observeAsState(emptyList())
    val coroutineScope = rememberCoroutineScope()


    fun validateInputs(): Boolean {
        var isValid = true

        if (customerName.isBlank() || customerName.any{ it.isDigit() }) {
            customerNameError = "Tên khách hàng không hợp lệ"
            isValid = false
        } else {
            customerNameError = null
        }

        if (phoneNumber.isBlank() || phoneNumber.length != 10 || !phoneNumber.any { it.isDigit() }) {
            phoneNumberError = "Số điện thoại không hợp lệ"
            isValid = false
        } else {
            phoneNumberError = null
        }

        if (totalPrice.isBlank() || totalPrice.toDoubleOrNull() == null || totalPrice.toDouble() < 0) {
            totalPriceError = "Tổng giá không hợp lệ"
            isValid = false
        } else {
            totalPriceError = null
        }

        if (status.isBlank() || status.any { it.isDigit() }) {
            statusError = "Trạng thái không hop lệ"
            isValid = false
        } else {
            statusError = null
        }

        return isValid
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        OutlinedTextField(
            value = customerName,
            onValueChange = {
                customerName = it
                if (customerNameError != null) customerNameError = null
            },
            label = { Text("Tên khách hàng") },
            modifier = Modifier.fillMaxWidth(),
            isError = customerNameError != null,
            supportingText = {
                customerNameError?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            }
        )

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = {
                phoneNumber = it
                if (phoneNumberError != null) phoneNumberError = null
            },
            label = { Text("Số điện thoại") },
            modifier = Modifier.fillMaxWidth(),
            isError = phoneNumberError != null,
            supportingText = {
                phoneNumberError?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            }
        )

        OutlinedTextField(
            value = totalPrice,
            onValueChange = {
                totalPrice = it
                if (totalPriceError != null) totalPriceError = null
            },
            label = { Text("Tổng giá") },
            modifier = Modifier.fillMaxWidth(),
            isError = totalPriceError != null,
            supportingText = {
                totalPriceError?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            }
        )

        OutlinedTextField(
            value = status,
            onValueChange = {
                status = it
                if (statusError != null) statusError = null
            },
            label = { Text("Trạng thái") },
            modifier = Modifier.fillMaxWidth(),
            isError = statusError != null,
            supportingText = {
                statusError?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            }
        )

        Button(
            onClick = {
                if (validateInputs()) {
                    val order = Order(
                        id = selectedOrder?.id ?: 0,
                        customer_name = customerName,
                        phone_number = phoneNumber,
                        total_price = totalPrice.toDouble(),
                        status = status
                    )
                    coroutineScope.launch {
                        if (selectedOrder == null) {
                            viewModel.addOrder(order.customer_name, order.phone_number, order.total_price, order.status)
                        } else {
                            viewModel.updateOrder(order)
                            selectedOrder = null
                        }
                    }
                    customerName = ""
                    phoneNumber = ""
                    totalPrice = ""
                    status = ""
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text(if (selectedOrder == null) "Thêm đơn hàng" else "Cập nhật đơn hàng")
        }

        LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 16.dp)) {
            items(orders) { order ->
                OrderItem(order, onEdit = {
                    customerName = it.customer_name
                    phoneNumber = it.phone_number
                    totalPrice = it.total_price.toString()
                    status = it.status
                    selectedOrder = it

                    customerNameError = null
                    phoneNumberError = null
                    totalPriceError = null
                    statusError = null
                }, onDelete = {
                    coroutineScope.launch {
                        viewModel.deleteOrderById(it.id)
                    }
                })
            }
        }
    }
}

@Composable
fun OrderItem(order: Order, onEdit: (Order) -> Unit, onDelete: (Order) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Khách hàng: ${order.customer_name}", style = MaterialTheme.typography.titleMedium)
                Text(text = "Số điện thoại: ${order.phone_number}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Tổng giá: ${order.total_price}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Trạng thái: ${order.status}", style = MaterialTheme.typography.bodyMedium)
            }
            Button(onClick = { onEdit(order) }) {
                Text("Sửa")
            }
            Button(onClick = { onDelete(order) }, modifier = Modifier.padding(start = 8.dp)) {
                Text("Xóa")
            }
        }
    }
}