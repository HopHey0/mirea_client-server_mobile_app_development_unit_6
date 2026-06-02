package data.dto

import kotlinx.serialization.Serializable


@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)


@Serializable
data class LoginResponse(val token: String)

@Serializable
data class ErrorResponse(val error: String)

@Serializable
data class MessageResponse(val message: String)

@Serializable
data class LaureateResponse(
    val id: Int,
    val prizeId: Int,
    val fullName: String,
    val motivation: String,
)

@Serializable
data class NobelPrizeResponse(
    val id: Int,
    val awardYear: String,
    val category: String,
    val detailLink: String,
    val laureates: List<LaureateResponse>
)
@Serializable
data class NobelPrizesResponse(
    val nobelPrizes: List<NobelPrizeResponse>
)
@Serializable
data class UserProfileResponse(
    val id: Int,
    val username: String,
    val role: String
)
