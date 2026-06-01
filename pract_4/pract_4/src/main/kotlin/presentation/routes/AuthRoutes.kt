package presentation.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import data.dto.ErrorResponse
import data.dto.LoginRequest
import data.dto.LoginResponse
import data.repository.InMemoryUserRepository
import domain.usecase.AuthenticateUserUseCase
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presentation.plugins.JWT_AUDIENCE
import presentation.plugins.JWT_ISSUER
import presentation.plugins.JWT_SECRET
import java.util.*

fun Route.authRoutes(authenticateUserUseCase: AuthenticateUserUseCase) {
    route("/auth") {
        post("/login") {
            val request = call.receive<LoginRequest>()

            val passwordHash = InMemoryUserRepository.hash(request.password)
            val user = authenticateUserUseCase.invoke(request.username, passwordHash)

            if (user == null) {
                call.respond(HttpStatusCode.Unauthorized, ErrorResponse("Invalid username or password"))
                return@post
            }

            val token = JWT.create()
                .withAudience(JWT_AUDIENCE)
                .withIssuer(JWT_ISSUER)
                .withClaim("username", user.username)
                .withExpiresAt(Date(System.currentTimeMillis() + 30 * 60 * 1000L))
                .sign(Algorithm.HMAC256(JWT_SECRET))

            call.respond(HttpStatusCode.OK, LoginResponse(token = token))
        }
    }
}