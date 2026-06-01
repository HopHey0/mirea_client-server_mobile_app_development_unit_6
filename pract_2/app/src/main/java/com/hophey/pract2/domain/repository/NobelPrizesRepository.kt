package com.hophey.pract2.domain.repository

import com.hophey.pract2.domain.entity.NobelPrize

interface NobelPrizesRepository {
    suspend fun getNobelPrizesByYearAndCategory(year: String): Result<List<NobelPrize>>
}