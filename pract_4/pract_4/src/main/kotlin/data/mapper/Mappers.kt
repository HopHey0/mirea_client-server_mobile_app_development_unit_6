package data.mapper

import data.dto.LaureateResponse
import data.dto.NobelPrizeResponse
import domain.entity.Laureate
import domain.entity.NobelPrize

fun NobelPrize.toResponse() = NobelPrizeResponse(
    awardYear = this.awardYear,
    category = this.category,
    laureates = this.laureates.map { it.toResponse() }
)

fun Laureate.toResponse() = LaureateResponse(
    id = this.id,
    fullName = this.fullName,
    motivation = this.motivation
)
