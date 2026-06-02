package presentation.routes

import data.dto.ErrorResponse
import data.dto.LaureateResponse
import data.dto.NobelPrizeResponse
import data.dto.NobelPrizesResponse
import data.mapper.toResponse
import domain.usecase.GetAllPrizesUseCase
import domain.usecase.GetLaureatesByPrizeUseCase
import domain.usecase.GetPrizeByIdUseCase
import domain.usecase.GetPrizeByYearUseCase
import io.github.smiley4.ktoropenapi.get
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.nobelPrizeRoutes(
    getAllPrizesUseCase: GetAllPrizesUseCase,
    getPrizeByIdUseCase: GetPrizeByIdUseCase,
    getLaureatesByPrizeUseCase: GetLaureatesByPrizeUseCase,
    getPrizeByYearUseCase: GetPrizeByYearUseCase
) {
    get("/prizes", {
        tags = listOf("Prizes")
        summary = "List all Nobel Prizes"
        description = "Returns all Nobel Prizes stored in the database."
        response {
            code(HttpStatusCode.OK) {
                description = "List of prizes"
                body<List<NobelPrizeResponse>>()
            }
        }
    }) {
        val prizes = getAllPrizesUseCase.invoke()
        call.respond(HttpStatusCode.OK, prizes.map { it.toResponse() })
    }

    get("/prizes/{prizeId}", {
        tags = listOf("Prizes")
        summary = "Get a Nobel Prize by ID"
        request {
            pathParameter<Int>("prizeId") {
                description = "Prize database ID"
            }
        }
        response {
            code(HttpStatusCode.OK) { body<NobelPrizeResponse>() }
            code(HttpStatusCode.NotFound) { body<ErrorResponse>() }
        }
    }) {
        val id = call.parameters["prizeId"]?.toIntOrNull()
            ?: throw IllegalArgumentException("Invalid 'prizeId' parameter")
        val prize = getPrizeByIdUseCase.invoke(id)
            ?: return@get call.respond(HttpStatusCode.NotFound, ErrorResponse("Prize not found"))
        call.respond(HttpStatusCode.OK, prize.toResponse())
    }

    get("/prizes/year/{prizeYear}", {
        tags = listOf("Prizes")
        summary = "Get a Nobel Prize by Year"
        request {
            pathParameter<String>("prizeYear") {
                description = "Prize database award year"
            }
        }
        response {
            code(HttpStatusCode.OK) { body<NobelPrizeResponse>() }
            code(HttpStatusCode.NotFound) { body<ErrorResponse>() }
        }
    }) {
        val year = call.parameters["prizeYear"]
            ?: throw IllegalArgumentException("Invalid 'prizeYear' parameter")
        val prizes = getPrizeByYearUseCase.invoke(year)
        call.respond(HttpStatusCode.OK, NobelPrizesResponse(nobelPrizes = prizes.map { it.toResponse() }))
    }

    get("/prizes/{prizeId}/laureates", {
        tags = listOf("Prizes")
        summary = "Get laureates for a prize"
        request {
            pathParameter<Int>("prizeId") {
                description = "Prize database ID"
            }
        }
        response {
            code(HttpStatusCode.OK) { body<List<LaureateResponse>>() }
            code(HttpStatusCode.NotFound) { body<ErrorResponse>() }
        }
    }) {
        val id = call.parameters["prizeId"]?.toIntOrNull()
            ?: throw IllegalArgumentException("Invalid 'prizeId' parameter")
        val laureates = getLaureatesByPrizeUseCase.invoke(id)
        call.respond(HttpStatusCode.OK, laureates.map { it.toResponse() })
    }
}