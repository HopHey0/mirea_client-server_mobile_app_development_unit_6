package presentation.routes

import data.dto.ErrorResponse
import data.dto.MessageResponse
import data.dto.NobelPrizeResponse
import data.dto.UserProfileResponse
import data.mapper.toProfileResponse
import data.mapper.toResponse
import domain.usecase.*
import io.github.smiley4.ktoropenapi.delete
import io.github.smiley4.ktoropenapi.get
import io.github.smiley4.ktoropenapi.post
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoutes(
    getUserByIdUseCase: GetUserByIdUseCase,
    getFavoritePrizesUseCase: GetFavoritePrizesUseCase,
    addFavoritePrizeUseCase: AddFavoritePrizeUseCase,
    removeFavoritePrizeUseCase: RemoveFavoritePrizeUseCase,
    getPrizeByIdUseCase: GetPrizeByIdUseCase
) {
    authenticate("auth-jwt") {

        get("/users/me", {
            tags = listOf("Users")
            summary = "Get current user profile"
            description = "Returns profile information for the authenticated user."
            securitySchemeNames("JWT")
            response {
                code(HttpStatusCode.OK) { body<UserProfileResponse>() }
                code(HttpStatusCode.Unauthorized) { body<ErrorResponse>() }
            }
        }) {
            val principal = call.principal<JWTPrincipal>()!!
            val userId = principal.payload.getClaim("userId").asInt()
            val user = getUserByIdUseCase.invoke(userId)
                ?: return@get call.respond(HttpStatusCode.NotFound, ErrorResponse("User not found"))
            call.respond(HttpStatusCode.OK, user.toProfileResponse())
        }

        get("/users/me/prizes", {
            tags = listOf("Users")
            summary = "Get favorite prizes"
            description = "Returns Nobel Prizes saved as favorites by the authenticated user."
            securitySchemeNames("JWT")
            response {
                code(HttpStatusCode.OK) { body<List<NobelPrizeResponse>>() }
                code(HttpStatusCode.Unauthorized) { body<ErrorResponse>() }
            }
        }) {
            val principal = call.principal<JWTPrincipal>()!!
            val userId = principal.payload.getClaim("userId").asInt()
            val favorites = getFavoritePrizesUseCase.invoke(userId)
            call.respond(HttpStatusCode.OK, favorites.map { it.toResponse() })
        }

        post("/users/me/prizes/{prizeId}", {
            tags = listOf("Users")
            summary = "Add prize to favorites"
            description = "Adds the specified Nobel Prize to the authenticated user's favorites."
            securitySchemeNames("JWT")
            request {
                pathParameter<Int>("prizeId") { description = "Prize database ID to add" }
            }
            response {
                code(HttpStatusCode.OK) { body<MessageResponse>() }
                code(HttpStatusCode.Conflict) { body<ErrorResponse>() }
                code(HttpStatusCode.NotFound) { body<ErrorResponse>() }
                code(HttpStatusCode.Unauthorized) { body<ErrorResponse>() }
            }
        }) {
            val principal = call.principal<JWTPrincipal>()!!
            val userId = principal.payload.getClaim("userId").asInt()
            val prizeId = call.parameters["prizeId"]?.toIntOrNull()
                ?: throw IllegalArgumentException("Invalid 'prizeId' parameter")

            getPrizeByIdUseCase.invoke(prizeId)
                ?: return@post call.respond(HttpStatusCode.NotFound, ErrorResponse("Prize not found"))

            val added = addFavoritePrizeUseCase.invoke(userId, prizeId)
            if (!added) {
                call.respond(HttpStatusCode.Conflict, ErrorResponse("Prize is already in favorites"))
            } else {
                call.respond(HttpStatusCode.OK, MessageResponse("Prize added to favorites"))
            }
        }

        delete("/users/me/prizes/{prizeId}", {
            tags = listOf("Users")
            summary = "Remove prize from favorites"
            description = "Removes the specified Nobel Prize from the authenticated user's favorites."
            securitySchemeNames("JWT")
            request {
                pathParameter<Int>("prizeId") { description = "Prize database ID to remove" }
            }
            response {
                code(HttpStatusCode.OK) { body<MessageResponse>() }
                code(HttpStatusCode.NotFound) { body<ErrorResponse>() }
                code(HttpStatusCode.Unauthorized) { body<ErrorResponse>() }
            }
        }) {
            val principal = call.principal<JWTPrincipal>()!!
            val userId = principal.payload.getClaim("userId").asInt()
            val prizeId = call.parameters["prizeId"]?.toIntOrNull()
                ?: throw IllegalArgumentException("Invalid 'prizeId' parameter")

            val removed = removeFavoritePrizeUseCase.invoke(userId, prizeId)
            if (!removed) {
                call.respond(HttpStatusCode.NotFound, ErrorResponse("Prize not found in favorites"))
            } else {
                call.respond(HttpStatusCode.OK, MessageResponse("Prize removed from favorites"))
            }
        }
    }
}
