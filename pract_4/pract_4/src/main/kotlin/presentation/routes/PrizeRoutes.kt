package presentation.routes

import data.dto.ErrorResponse
import data.mapper.toResponse
import domain.usecase.GetAllPrizesUseCase
import domain.usecase.GetLaureatesUseCase
import domain.usecase.GetPrizeUseCase
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.nobelPrizeRoutes(
    getAllPrizesUseCase: GetAllPrizesUseCase,
    getPrizeUseCase: GetPrizeUseCase,
    getLaureatesUseCase: GetLaureatesUseCase
) {
    authenticate("auth-jwt") {

        get("/prizes") {
            val prizes = getAllPrizesUseCase.invoke()
            call.respond(HttpStatusCode.OK, prizes.map { it.toResponse() })
        }

        get("/prizes/{year}/{category}") {
            val year = call.parameters["year"]
                ?: throw IllegalArgumentException("Missing 'year' path parameter")
            val category = call.parameters["category"]
                ?: throw IllegalArgumentException("Missing 'category' path parameter")

            val prize = getPrizeUseCase.invoke(year, category)
                ?: return@get call.respond(
                    HttpStatusCode.NotFound,
                    ErrorResponse("No prize found for year=$year, category=$category")
                )

            call.respond(HttpStatusCode.OK, prize.toResponse())
        }

        get("/prizes/{year}/{category}/laureates") {
            val year = call.parameters["year"]
                ?: throw IllegalArgumentException("Missing 'year' path parameter")
            val category = call.parameters["category"]
                ?: throw IllegalArgumentException("Missing 'category' path parameter")

            val laureates = getLaureatesUseCase.invoke(year, category)
                ?: return@get call.respond(
                    HttpStatusCode.NotFound,
                    ErrorResponse("No prize found for year=$year, category=$category")
                )

            call.respond(HttpStatusCode.OK, laureates.map { it.toResponse() })
        }
    }
}