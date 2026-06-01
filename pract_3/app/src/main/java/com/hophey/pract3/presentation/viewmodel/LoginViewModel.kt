package com.hophey.pract3.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hophey.pract3.domain.usecase.LoginUseCase
import com.hophey.pract3.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginFormState(
    val username: String = "",
    val password: String = ""
)

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _formState = MutableStateFlow(LoginFormState())
    val formState: StateFlow<LoginFormState> = _formState.asStateFlow()

    fun onUsernameChange(value: String) {
        _formState.value = _formState.value.copy(username = value)
    }

    fun onPasswordChange(value: String) {
        _formState.value = _formState.value.copy(password = value)
    }

    fun login() {
        if (_uiState.value is UiState.Loading) return

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val form = _formState.value
            val result = loginUseCase(form.username, form.password)
            result.fold(
                onSuccess = {
                    _uiState.value = UiState.Success(Unit)
                },
                onFailure = { error ->
                    _uiState.value = UiState.Error(error.message ?: "Unknown error")
                }
            )
        }
    }

    fun resetState() {
        _uiState.value = UiState.Idle
    }
}