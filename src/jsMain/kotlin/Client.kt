import react.create
import react.dom.client.createRoot
import telegram_api.TelegramWebApp
import web.dom.document


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