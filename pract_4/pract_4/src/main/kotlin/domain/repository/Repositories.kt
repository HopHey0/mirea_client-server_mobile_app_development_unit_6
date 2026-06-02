package domain.repository

import domain.entity.Laureate
import domain.entity.NobelPrize
import domain.entity.User

interface NobelPrizeRepository {
    fun getAllPrizes(): List<NobelPrize>
    fun getPrizeById(id: Int): NobelPrize?
    fun getLaureatesByPrizeId(prizeId: Int): List<Laureate>
    fun getPrizeByYear(year: String): List<NobelPrize>
}

interface UserRepository {
    fun findByUsername(username: String): User?
    fun findById(id: Int): User?
}

interface UserPrizeRepository {
    fun getFavorites(userId: Int): List<NobelPrize>
    fun addFavorite(userId: Int, prizeId: Int): Boolean
    fun removeFavorite(userId: Int, prizeId: Int): Boolean
    fun isFavorite(userId: Int, prizeId: Int): Boolean
}
