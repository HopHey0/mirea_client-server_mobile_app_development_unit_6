package com.hophey.pract3.domain.repository

import com.hophey.pract3.domain.entity.AuthResult

interface AuthRepository {

    suspend fun login(username: String, password: String): Result<AuthResult>

    suspend fun logout()

    suspend fun getToken(): String?
}