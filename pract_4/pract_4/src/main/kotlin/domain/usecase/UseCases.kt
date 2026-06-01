package domain.usecase

import domain.entity.Laureate
import domain.entity.NobelPrize
import domain.entity.User
import domain.repository.NobelPrizeRepository
import domain.repository.UserRepository

class GetAllPrizesUseCase(private val repository: NobelPrizeRepository) {
    fun invoke(): List<NobelPrize> = repository.getAllPrizes()
}

class GetPrizeUseCase(private val repository: NobelPrizeRepository) {
    fun invoke(year: String, category: String): NobelPrize? =
        repository.getPrize(year, category)
}

class GetLaureatesUseCase(private val repository: NobelPrizeRepository) {
    fun invoke(year: String, category: String): List<Laureate>? =
        repository.getLaureates(year, category)
}

class AuthenticateUserUseCase(private val userRepository: UserRepository) {
    fun invoke(username: String, passwordHash: String): User? {
        val user = userRepository.findByUsername(username) ?: return null
        return if (user.passwordHash == passwordHash) user else null
    }
}
