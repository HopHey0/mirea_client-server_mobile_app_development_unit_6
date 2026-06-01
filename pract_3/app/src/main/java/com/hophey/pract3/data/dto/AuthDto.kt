package com.hophey.pract3.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val username: String,
    val password: String,
    val expiresInMins: Int = 30
)

@Serializable
data class LoginResponseDto(
    val id: Int,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val image: String,
    val accessToken: String,
    val refreshToken: String = ""
)