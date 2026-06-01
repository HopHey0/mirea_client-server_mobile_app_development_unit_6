package com.hophey.pract3.domain.entity

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val image: String,
    val token: String = ""
)