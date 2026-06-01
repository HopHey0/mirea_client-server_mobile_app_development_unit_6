package com.hophey.pract3.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.hophey.pract3.domain.entity.User
import com.hophey.pract3.presentation.viewmodel.UsersListViewModel
import com.hophey.pract3.utils.UiState
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersListScreen(
    onUserClick: (Int) -> Unit,
    onLogout: () -> Unit,
    viewModel: UsersListViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Пользователи") },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.logout()
                            onLogout()
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Outlined.ExitToApp,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { padding ->

        when (uiState) {

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
                    Text(uiState.message)
                }
            }

            is UiState.Success<List<User>> -> {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(8.dp)
                ) {

                    items(uiState.data) { user ->

                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onUserClick(user.id)
                                }
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                AsyncImage(
                                    model = user.image,
                                    contentDescription = null,
                                    modifier = Modifier.size(64.dp)
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column {

                                    Text(
                                        text = "${user.firstName} ${user.lastName}",
                                        style = MaterialTheme.typography.titleMedium
                                    )

                                    Text(
                                        text = user.email,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}