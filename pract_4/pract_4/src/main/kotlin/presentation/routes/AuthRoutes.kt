package presentation.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import data.dto.ErrorResponse
import data.dto.LoginRequest
import data.dto.LoginResponse
import data.repository.UserRepositoryImpl
import domain.usecase.AuthenticateUserUseCase
import io.github.smiley4.ktoropenapi.post
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import presentation.plugins.JWT_AUDIENCE
import presentation.plugins.JWT_ISSUER
import presentation.plugins.JWT_SECRET
import java.util.*

fun Route.authRoutes(authenticateUserUseCase: AuthenticateUserUseCase) {

    post("/login", {
        tags = listOf("Auth")
        summary = "Authenticate and receive a JWT token"
        description = "Pass username and password to receive a Bearer token valid for 30 minutes."
        request {
            body<LoginRequest> {
                description = "Credentials"
                example("admin") {
                    value = LoginRequest("admin", "admin123")
                }
                example("user") {
                    value = LoginRequest("user", "password")
                }
            }
        }
        response {
            code(HttpStatusCode.OK) {
                description = "Successful login — returns JWT token"
                body<LoginResponse>()
            }
            code(HttpStatusCode.Unauthorized) {
                description = "Invalid credentials"
                body<ErrorResponse>()
            }
        }
    }) {
        val request = call.receive<LoginRequest>()
        val passwordHash = UserRepositoryImpl.hash(request.password)
        val user = authenticateUserUseCase.invoke(request.username, passwordHash)

        if (user == null) {
            call.respond(HttpStatusCode.Unauthorized, ErrorResponse("Invalid username or password"))
            return@post
        }

        val token = JWT.create()
            .withAudience(JWT_AUDIENCE)
            .withIssuer(JWT_ISSUER)
            .withClaim("userId", user.id)
            .withClaim("username", user.username)
            .withClaim("role", user.role)
            .withExpiresAt(Date(System.currentTimeMillis() + 30 * 60 * 1000L))
            .sign(Algorithm.HMAC256(JWT_SECRET))

        call.respond(HttpStatusCode.OK, LoginResponse(token = token))
    }
}