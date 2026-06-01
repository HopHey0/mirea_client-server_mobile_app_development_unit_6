package presentation.plugins

import di.AppContainer
import io.ktor.server.application.*
import io.ktor.server.routing.*
import presentation.routes.authRoutes
import presentation.routes.nobelPrizeRoutes

fun Application.configureRouting() {
    routing {
        authRoutes(AppContainer.authenticateUserUseCase)
        nobelPrizeRoutes(
            getAllPrizesUseCase = AppContainer.getAllPrizesUseCase,
            getPrizeUseCase = AppContainer.getPrizeUseCase,
            getLaureatesUseCase = AppContainer.getLaureatesUseCase
        )
    }
}
