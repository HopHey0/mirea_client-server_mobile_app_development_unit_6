package presentation.plugins

import di.AppContainer
import io.ktor.server.application.*
import io.ktor.server.routing.*
import presentation.routes.authRoutes
import presentation.routes.nobelPrizeRoutes
import presentation.routes.userRoutes

fun Application.configureRouting() {
    routing {
        authRoutes(AppContainer.authenticateUserUseCase)

        nobelPrizeRoutes(
            getAllPrizesUseCase = AppContainer.getAllPrizesUseCase,
            getPrizeByIdUseCase = AppContainer.getPrizeByIdUseCase,
            getLaureatesByPrizeUseCase = AppContainer.getLaureatesByPrizeUseCase,
            getPrizeByYearUseCase = AppContainer.getPrizeByYearUseCase,
        )

        userRoutes(
            getUserByIdUseCase = AppContainer.getUserByIdUseCase,
            getFavoritePrizesUseCase = AppContainer.getFavoritePrizesUseCase,
            addFavoritePrizeUseCase = AppContainer.addFavoritePrizeUseCase,
            removeFavoritePrizeUseCase = AppContainer.removeFavoritePrizeUseCase,
            getPrizeByIdUseCase = AppContainer.getPrizeByIdUseCase
        )
    }
}
