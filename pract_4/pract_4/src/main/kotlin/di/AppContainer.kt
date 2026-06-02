package di

import data.repository.NobelPrizeRepositoryImpl
import data.repository.UserPrizeRepositoryImpl
import data.repository.UserRepositoryImpl
import domain.usecase.*

object AppContainer {

    private val nobelPrizeRepository by lazy { NobelPrizeRepositoryImpl() }
    private val userRepository by lazy { UserRepositoryImpl() }
    private val userPrizeRepository by lazy { UserPrizeRepositoryImpl() }

    val getAllPrizesUseCase by lazy { GetAllPrizesUseCase(nobelPrizeRepository) }
    val getPrizeByIdUseCase by lazy { GetPrizeByIdUseCase(nobelPrizeRepository) }
    val getLaureatesByPrizeUseCase by lazy { GetLaureatesByPrizeUseCase(nobelPrizeRepository) }

    val authenticateUserUseCase by lazy { AuthenticateUserUseCase(userRepository) }
    val getUserByIdUseCase by lazy { GetUserByIdUseCase(userRepository) }

    val getFavoritePrizesUseCase by lazy { GetFavoritePrizesUseCase(userPrizeRepository) }
    val addFavoritePrizeUseCase by lazy { AddFavoritePrizeUseCase(userPrizeRepository) }
    val removeFavoritePrizeUseCase by lazy { RemoveFavoritePrizeUseCase(userPrizeRepository) }
    val getPrizeByYearUseCase by lazy { GetPrizeByYearUseCase(nobelPrizeRepository) }
}
