package com.hophey.pract2.domain.useCase

import com.hophey.pract2.domain.repository.NobelPrizesRepository

class GetNobelPrizesByYearAndCategoryUseCase(
    private val nobelPrizesRepository: NobelPrizesRepository
) {
    suspend operator fun invoke(query: String) = nobelPrizesRepository.getNobelPrizesByYearAndCategory(query)
}