import io.ktor.server.application.*
import io.ktor.server.netty.*
import presentation.plugins.configureLogging
import presentation.plugins.configureRouting
import presentation.plugins.configureSecurity
import presentation.plugins.configureSerialization
import presentation.plugins.configureStatusPages

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureSerialization()
    configureLogging()
    configureStatusPages()
    configureSecurity()
    configureRouting()
}
