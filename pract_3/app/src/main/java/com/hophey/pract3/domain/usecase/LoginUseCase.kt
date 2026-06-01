package com.hophey.pract3.domain.usecase

import com.hophey.pract3.domain.entity.AuthResult
import com.hophey.pract3.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String): Result<AuthResult> {
        if (username.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("Введите логин и пароль"))
        }
        return authRepository.login(username, password)
    }
}