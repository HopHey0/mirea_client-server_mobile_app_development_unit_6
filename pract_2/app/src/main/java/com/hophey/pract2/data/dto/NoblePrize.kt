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
    val id: Int,
    val awardYear: String,
    val category: String,
    val detailLink: String,
    val laureates: List<LaureateDto>
)

@Serializable
data class LaureateDto(
    val id: Int,
    val prizeId: Int,
    val fullName: String,
    val motivation: String
)

fun NobelPrizeDto.toEntity() = NobelPrize(
    awardYear = awardYear,
    category = category,
    laureates = laureates.map { it.toEntity() }
)

fun LaureateDto.toEntity() = Laureate(
    id = id.toString(),
    fullName = fullName,
    motivation = motivation
)