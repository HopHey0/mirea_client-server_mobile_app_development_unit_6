package di

import data.repository.InMemoryNobelPrizeRepository
import data.repository.InMemoryUserRepository
import domain.usecase.AuthenticateUserUseCase
import domain.usecase.GetAllPrizesUseCase
import domain.usecase.GetLaureatesUseCase
import domain.usecase.GetPrizeUseCase

object AppContainer {

    private val nobelPrizeRepository by lazy { InMemoryNobelPrizeRepository() }
    private val userRepository by lazy { InMemoryUserRepository() }

    val getAllPrizesUseCase by lazy { GetAllPrizesUseCase(nobelPrizeRepository) }
    val getPrizeUseCase by lazy { GetPrizeUseCase(nobelPrizeRepository) }
    val getLaureatesUseCase by lazy { GetLaureatesUseCase(nobelPrizeRepository) }
    val authenticateUserUseCase by lazy { AuthenticateUserUseCase(userRepository) }
}
