package su.pank.application

import io.ktor.http.*
import io.ktor.network.tls.certificates.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import kotlinx.html.*
import java.io.File

fun HTML.index() {
    head {
        title("Hello from Ktor!")
        script(src = "https://telegram.org/js/telegram-web-app.js") {}
        style {
            +"@import url('https://fonts.googleapis.com/css2?family=Roboto+Serif&display=swap');"
        }
    }
    body {
        div {
            id = "root"
        }

        script(src = "test_web.js") {}


    }
}

fun main() {
    val keyStoreFile = File("build/keystore.jks")

    val keyStore = buildKeyStore {
        certificate("sampleAlias") {
            password = "test123"
            domains = listOf("127.0.0.1", "0.0.0.0", "localhost")
        }

    }
    keyStore.saveToFile(keyStoreFile, "test123")

    val environment = applicationEngineEnvironment {
        connector {
            port = 8080
        }
        sslConnector(
            keyStore = keyStore,
            keyAlias = "sampleAlias",
            keyStorePassword = { "test123".toCharArray() },
            privateKeyPassword = { "test123".toCharArray() }) {
            port = 443
            keyStorePath = keyStoreFile
        }
        module(Application::module)
    }

    embeddedServer(Netty, environment = environment).start(wait = true)
}


fun Application.module() {
    routing {
        get("/") {
            call.respondHtml(HttpStatusCode.OK, HTML::index)
        }
        static("/") {
            resources()
        }
    }
}