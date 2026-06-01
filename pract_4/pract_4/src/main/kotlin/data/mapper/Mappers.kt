package data.mapper

import data.dto.LaureateResponse
import data.dto.NobelPrizeResponse
import data.dto.UserProfileResponse
import domain.entity.Laureate
import domain.entity.NobelPrize
import domain.entity.User

fun NobelPrize.toResponse() = NobelPrizeResponse(
    id = this.id,
    awardYear = this.awardYear,
    category = this.category,
    detailLink = this.detailLink,
    laureates = this.laureates.map { it.toResponse() }
)

fun Laureate.toResponse() = LaureateResponse(
    id = this.id,
    prizeId = this.prizeId,
    fullName = this.fullName,
    motivation = this.motivation,
)

fun User.toProfileResponse() = UserProfileResponse(
    id = this.id,
    username = this.username,
    role = this.role
)

