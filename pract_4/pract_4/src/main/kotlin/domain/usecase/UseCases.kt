package domain.usecase

import domain.entity.Laureate
import domain.entity.NobelPrize
import domain.entity.User
import domain.repository.NobelPrizeRepository
import domain.repository.UserPrizeRepository
import domain.repository.UserRepository

class GetAllPrizesUseCase(private val repository: NobelPrizeRepository) {
    fun invoke(): List<NobelPrize> = repository.getAllPrizes()
}

class GetPrizeByIdUseCase(private val repository: NobelPrizeRepository) {
    fun invoke(id: Int): NobelPrize? = repository.getPrizeById(id)
}

class GetPrizeByYearUseCase(private val repository: NobelPrizeRepository) {
    fun invoke(year: String): List<NobelPrize> = repository.getPrizeByYear(year)
}

class GetLaureatesByPrizeUseCase(private val repository: NobelPrizeRepository) {
    fun invoke(prizeId: Int): List<Laureate> = repository.getLaureatesByPrizeId(prizeId)
}

class AuthenticateUserUseCase(private val userRepository: UserRepository) {
    fun invoke(username: String, passwordHash: String): User? {
        val user = userRepository.findByUsername(username) ?: return null
        return if (user.passwordHash == passwordHash) user else null
    }
}

class GetUserByIdUseCase(private val userRepository: UserRepository) {
    fun invoke(id: Int): User? = userRepository.findById(id)
}


class GetFavoritePrizesUseCase(private val userPrizeRepository: UserPrizeRepository) {
    fun invoke(userId: Int): List<NobelPrize> = userPrizeRepository.getFavorites(userId)
}

class AddFavoritePrizeUseCase(private val userPrizeRepository: UserPrizeRepository) {
    fun invoke(userId: Int, prizeId: Int): Boolean = userPrizeRepository.addFavorite(userId, prizeId)
}

class RemoveFavoritePrizeUseCase(private val userPrizeRepository: UserPrizeRepository) {
    fun invoke(userId: Int, prizeId: Int): Boolean = userPrizeRepository.removeFavorite(userId, prizeId)
}

