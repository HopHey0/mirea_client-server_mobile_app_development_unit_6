package com.hophey.pract3.domain.repository

import com.hophey.pract3.domain.entity.User

interface UsersRepository {
    suspend fun getUsers(): Result<List<User>>

    suspend fun getUserById(id: Int): Result<User>
}