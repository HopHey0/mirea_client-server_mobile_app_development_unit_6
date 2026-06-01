package domain.repository

import domain.entity.Laureate
import domain.entity.NobelPrize
import domain.entity.User

interface NobelPrizeRepository {
    fun getAllPrizes(): List<NobelPrize>
    fun getPrize(year: String, category: String): NobelPrize?
    fun getLaureates(year: String, category: String): List<Laureate>?
}

interface UserRepository {
    fun findByUsername(username: String): User?
}
