import react.create
import react.dom.client.createRoot
import web.cssom.Color
import web.dom.document
import kotlin.math.log




fun main() {
    val container = document.createElement("div")
    document.body.appendChild(container)

    val telegramWebApp = TelegramWebApp()
    println(telegramWebApp)

    val welcome = Welcome.create {
        name = "Hello 2"
        app = telegramWebApp
    }
    createRoot(container).render(welcome)
}