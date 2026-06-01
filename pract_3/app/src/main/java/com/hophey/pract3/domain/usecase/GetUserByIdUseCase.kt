package com.hophey.pract3.domain.usecase

import com.hophey.pract3.domain.entity.User
import com.hophey.pract3.domain.repository.UsersRepository

class GetUserByIdUseCase(
    private val usersRepository: UsersRepository
) {
    suspend operator fun invoke(id: Int): Result<User> {
        return usersRepository.getUserById(id)
    }
}