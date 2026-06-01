package com.hophey.pract2.data.repository

import com.hophey.pract2.data.dto.toEntity
import com.hophey.pract2.data.network.api.NobelPrizesApi
import com.hophey.pract2.domain.entity.NobelPrize
import com.hophey.pract2.domain.repository.NobelPrizesRepository

class NobelPrizesRepositoryImpl(
    private val api: NobelPrizesApi
) : NobelPrizesRepository {
    override suspend fun getNobelPrizesByYearAndCategory(year: String): Result<List<NobelPrize>> = runCatching {
        api.getNobelPrizesByParams(year).nobelPrizes.map { it.toEntity() }
    }
}