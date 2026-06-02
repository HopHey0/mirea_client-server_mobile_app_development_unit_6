import data.db.DatabaseConfig
import io.ktor.server.application.*
import io.ktor.server.netty.*
import presentation.plugins.configureLogging
import presentation.plugins.configureOpenApi
import presentation.plugins.configureRouting
import presentation.plugins.configureSecurity
import presentation.plugins.configureSerialization
import presentation.plugins.configureStatusPages
import presentation.plugins.configureSwaggerRoutes

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    DatabaseConfig.connect()

    configureSerialization()
    configureLogging()
    configureStatusPages()
    configureSecurity()
    configureOpenApi()
    configureRouting()
    configureSwaggerRoutes()
}
