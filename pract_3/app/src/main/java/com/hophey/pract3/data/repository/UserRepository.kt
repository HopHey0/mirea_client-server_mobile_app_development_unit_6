package com.hophey.pract3.data.repository


import com.hophey.pract3.data.datastore.TokenDataStore
import com.hophey.pract3.data.network.Api
import com.hophey.pract3.domain.entity.User
import com.hophey.pract3.domain.repository.UsersRepository
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import java.io.IOException

class UsersRepositoryImpl(
    private val api: Api,
    private val tokenDataStore: TokenDataStore
) : UsersRepository {

    override suspend fun getUsers(): Result<List<User>> = runCatching {
        val token = tokenDataStore.getToken() ?: throw Exception("You are not authorized")
        val response = api.getUsers(token)
        response.users.map { dto ->
            User(
                id = dto.id,
                firstName = dto.firstName,
                lastName = dto.lastName,
                username = dto.username,
                email = dto.email,
                image = dto.image
            )
        }
    }

    override suspend fun getUserById(id: Int): Result<User> = runCatching {
        val token = tokenDataStore.getToken() ?: throw Exception("You are not authorized")
        val dto = api.getUserById(id, token)
        User(
            id = dto.id,
            firstName = dto.firstName,
            lastName = dto.lastName,
            username = dto.username,
            email = dto.email,
            image = dto.image
        )
    }
}