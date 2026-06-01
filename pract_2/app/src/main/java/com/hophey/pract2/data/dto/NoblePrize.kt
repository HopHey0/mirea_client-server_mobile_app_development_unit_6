package com.hophey.pract2.data.dto

import com.hophey.pract2.domain.entity.Laureate
import com.hophey.pract2.domain.entity.NobelPrize
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
    fullName = this.fullName?.en ?: "",
    motivation = this.motivation?.en ?: ""
)