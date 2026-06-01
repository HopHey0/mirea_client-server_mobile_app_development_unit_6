package com.hophey.pract3.data.network

import com.hophey.pract3.data.dto.LoginRequestDto
import com.hophey.pract3.data.dto.LoginResponseDto
import com.hophey.pract3.data.dto.UserDto
import com.hophey.pract3.data.dto.UsersResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

class Api(
    private val client: HttpClient
) {
    companion object {
        private const val BASE_URL = "https://dummyjson.com"
    }

    suspend fun login(request: LoginRequestDto): LoginResponseDto {
        return client.post("$BASE_URL/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    suspend fun getUsers(token: String): UsersResponseDto {
        return client.get("$BASE_URL/users") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()
    }

    suspend fun getUserById(id: Int, token: String): UserDto {
        return client.get("$BASE_URL/users/$id") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()
    }
}