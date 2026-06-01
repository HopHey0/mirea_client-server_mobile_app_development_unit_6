package com.hophey.pract3.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hophey.pract3.domain.entity.User
import com.hophey.pract3.domain.usecase.GetUsersUseCase
import com.hophey.pract3.domain.usecase.LogoutUseCase
import com.hophey.pract3.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsersListViewModel(
    private val getUsersUseCase: GetUsersUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<UiState<List<User>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<User>>> = _uiState.asStateFlow()

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            getUsersUseCase().fold(
                onSuccess = {
                    _uiState.value = UiState.Success(it)
                },
                onFailure = {
                    _uiState.value =
                        UiState.Error(it.message ?: "Ошибка загрузки")
                }
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }
}