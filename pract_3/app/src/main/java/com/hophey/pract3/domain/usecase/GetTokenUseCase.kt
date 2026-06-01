package com.hophey.pract3.domain.usecase

import com.hophey.pract3.domain.repository.AuthRepository

class GetTokenUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): String? {
        return authRepository.getToken()
    }
}