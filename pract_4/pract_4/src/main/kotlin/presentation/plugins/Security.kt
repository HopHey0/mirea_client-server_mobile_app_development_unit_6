package presentation.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

const val JWT_SECRET = "this_key_contains_exactly_40_symbols_lol"
const val JWT_ISSUER = "nobel-prize-api"
const val JWT_AUDIENCE = "nobel-prize-client"
const val JWT_REALM = "Nobel Prize API"

fun Application.configureSecurity() {
    val algorithm = Algorithm.HMAC256(JWT_SECRET)

    install(Authentication) {
        jwt("auth-jwt") {
            realm = JWT_REALM
            verifier(
                JWT.require(algorithm)
                    .withAudience(JWT_AUDIENCE)
                    .withIssuer(JWT_ISSUER)
                    .build()
            )
            validate { credential ->
                if (credential.payload.getClaim("username").asString().isNotBlank()) {
                    JWTPrincipal(credential.payload)
                } else null
            }
        }
    }
}
