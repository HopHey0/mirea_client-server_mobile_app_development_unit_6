package com.hophey.pract3.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hophey.pract3.domain.entity.User
import com.hophey.pract3.domain.usecase.GetUserByIdUseCase
import com.hophey.pract3.domain.usecase.LogoutUseCase
import com.hophey.pract3.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<UiState<User>>(UiState.Loading)
    val uiState: StateFlow<UiState<User>> = _uiState.asStateFlow()

    fun loadUser(id: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            getUserByIdUseCase(id).fold(
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
}