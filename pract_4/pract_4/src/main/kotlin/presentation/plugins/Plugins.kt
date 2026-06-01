package presentation.plugins

import data.dto.ErrorResponse
import io.github.smiley4.ktoropenapi.OpenApi
import io.github.smiley4.ktoropenapi.config.AuthScheme
import io.github.smiley4.ktoropenapi.config.AuthType
import io.github.smiley4.ktoropenapi.openApi
import io.github.smiley4.ktorswaggerui.swaggerUI
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.slf4j.event.Level

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}

fun Application.configureLogging() {
    install(CallLogging) {
        level = Level.INFO
        format { call ->
            val status = call.response.status()
            val method = call.request.local.method.value
            val uri = call.request.local.uri
            "[$method] $uri → ${status?.value ?: "???"}"
        }
    }
}

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<IllegalArgumentException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, ErrorResponse(cause.message ?: "Bad request"))
        }
        exception<Throwable> { call, cause ->
            call.application.environment.log.error("Unhandled exception", cause)
            call.respond(HttpStatusCode.InternalServerError, ErrorResponse("Internal server error: ${cause.message}"))
        }
        status(HttpStatusCode.Unauthorized) { call, _ ->
            call.respond(HttpStatusCode.Unauthorized, ErrorResponse("Unauthorized"))
        }
        status(HttpStatusCode.NotFound) { call, _ ->
            call.respond(HttpStatusCode.NotFound, ErrorResponse("Resource not found"))
        }
    }
}

fun Application.configureOpenApi() {
    install(OpenApi) {
        info {
            title = "Nobel Prize API"
            version = "1.0.0"
            description = """
                REST API для просмотра данных о Нобелевских премиях.
                Используйте POST /login для получения JWT-токена
            """.trimIndent()
        }
        server {
            url = "http://localhost:8081"
            description = "Local dev server"
        }
        security {
            securityScheme("JWT") {
                type = AuthType.HTTP
                scheme = AuthScheme.BEARER
                bearerFormat = "JWT"
            }
        }
    }
}

fun Application.configureSwaggerRoutes() {
    routing {
        route("api.json") {
            openApi()
        }
        route("swagger") {
            swaggerUI("/api.json")
        }
    }
}