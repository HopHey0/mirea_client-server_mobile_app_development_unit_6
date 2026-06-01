package com.hophey.pract3.domain.entity

data class AuthResult(
    val token: String,
    val user: User
)