package com.hophey.pract3.domain.usecase

import com.hophey.pract3.domain.entity.User
import com.hophey.pract3.domain.repository.UsersRepository

class GetUsersUseCase(
    private val usersRepository: UsersRepository
) {
    suspend operator fun invoke(): Result<List<User>> {
        return usersRepository.getUsers()
    }
}