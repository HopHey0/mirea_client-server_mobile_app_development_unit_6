package data.dto

import domain.entity.Laureate
import domain.entity.NobelPrize
import kotlinx.serialization.Serializable

@Serializable
data class NobelPrizesResponse(
    val nobelPrizes: List<NobelPrizeDto>
)

@Serializable
data class NobelPrizeDto(
    val awardYear: String,
    val category: LocalizedStringDto,
    val laureates: List<LaureateShortDto> = emptyList()
)

fun NobelPrizeDto.toEntity() = NobelPrize(
    awardYear = this.awardYear,
    category = this.category.en,
    laureates = this.laureates.map { dtoLaureate -> dtoLaureate.toEntity() }
)

@Serializable
data class LocalizedStringDto(
    val en: String
)

@Serializable
data class LaureateShortDto(
    val id: String,
    val fullName: LocalizedStringDto? = null,
    val orgName: LocalizedStringDto? = null,
    val motivation: LocalizedStringDto? = null
)

fun LaureateShortDto.toEntity() = Laureate(
    id = this.id,
    fullName = this.fullName?.en ?: this.orgName?.en ?: "",
    motivation = this.motivation?.en ?: ""
)

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val token: String
)

@Serializable
data class NobelPrizeResponse(
    val awardYear: String,
    val category: String,
    val laureates: List<LaureateResponse>
)

@Serializable
data class LaureateResponse(
    val id: String,
    val fullName: String,
    val motivation: String
)

@Serializable
data class ErrorResponse(val error: String)