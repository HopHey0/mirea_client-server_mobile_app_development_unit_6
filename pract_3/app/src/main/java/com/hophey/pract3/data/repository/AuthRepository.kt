package com.hophey.pract3.data.repository


import com.hophey.pract3.data.datastore.TokenDataStore
import com.hophey.pract3.data.dto.LoginRequestDto
import com.hophey.pract3.data.network.Api
import com.hophey.pract3.domain.entity.AuthResult
import com.hophey.pract3.domain.entity.User
import com.hophey.pract3.domain.repository.AuthRepository


class AuthRepositoryImpl (
    private val api: Api,
    private val tokenDataStore: TokenDataStore
) : AuthRepository {

    override suspend fun login(username: String, password: String): Result<AuthResult> = runCatching {
        val response = api.login(LoginRequestDto(username = username, password = password))
        val authResult = AuthResult(
            token = response.accessToken,
            user = User(
                id = response.id,
                firstName = response.firstName,
                lastName = response.lastName,
                username = response.username,
                email = response.email,
                image = response.image,
                token = response.accessToken
            )
        )
        tokenDataStore.saveToken(response.accessToken)
        authResult
    }

    override suspend fun logout() {
        tokenDataStore.clearToken()
    }

    override suspend fun getToken(): String? {
        return tokenDataStore.getToken()
    }
}