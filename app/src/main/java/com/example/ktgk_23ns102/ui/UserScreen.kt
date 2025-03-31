package com.example.ktgk_23ns102.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.ktgk_23ns102.model.data.User
import com.example.ktgk_23ns102.viewmodel.UserViewModel

@Composable
fun UserScreen(viewModel: UserViewModel = viewModel()) {
    val users by viewModel.userList

    LaunchedEffect(Unit) {
        viewModel.fetchUsers()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Danh sách người dùng", style = MaterialTheme.typography.headlineSmall)

        LazyColumn {
            items(users) { user ->
                UserItem(user)
            }
        }
    }
}

@Composable
fun UserItem(user: User) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(user.avatar),
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "${user.firstName} ${user.lastName}", style = MaterialTheme.typography.bodyLarge)
                Text(text = user.email, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}