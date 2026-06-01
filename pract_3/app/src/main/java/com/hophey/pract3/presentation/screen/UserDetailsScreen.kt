package com.hophey.pract3.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.hophey.pract3.domain.entity.User
import com.hophey.pract3.presentation.viewmodel.UserDetailViewModel
import com.hophey.pract3.utils.UiState
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    userId: Int,
    onBack: () -> Unit,
    viewModel: UserDetailViewModel = koinViewModel()
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(userId) {
        viewModel.loadUser(userId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Профиль") },

                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
            )
        }
    ) { padding ->

        when (val state = uiState) {

            UiState.Idle -> {}

            UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(state.message)
                }
            }

            is UiState.Success<User> -> {

                val user = state.data

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    AsyncImage(
                        model = user.image,
                        contentDescription = null,
                        modifier = Modifier.size(150.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "${user.firstName} ${user.lastName}",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = user.username,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = user.email,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "ID: ${user.id}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}