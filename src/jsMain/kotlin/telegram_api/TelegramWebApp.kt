package telegram_api

import web.cssom.Color

class TelegramWebApp {

    private val webApp: dynamic = js("window.Telegram.WebApp")

    val colorScheme: String
    val platform: String
    val version: String
    val initData: String
    val isExpanded: Boolean
    val viewportHeight: Float
    val viewportStableHeight: Float
    val headerColor: Color
    val backgroundColor: Color
    val isClosingConfirmationEnabled: Boolean

    fun openLink(url: String, options: dynamic = undefined) {
        webApp.openLink(url, options)
    }

    fun close() {
        webApp.close()
    }

    fun expand() {
        webApp.expand()
    }

    init {
        console.log(webApp)
        colorScheme = webApp.colorScheme as String
        platform = webApp.platform as String
        version = webApp.version as String
        initData = webApp.initData as String
        isExpanded = webApp.isExpanded as Boolean
        viewportHeight = webApp.viewportHeight as Float
        viewportStableHeight = webApp.viewportStableHeight as Float

        headerColor = Color((webApp.headerColor ?: "#ffffff") as String)
        backgroundColor = Color((webApp.backgroundColor ?: "#ffffff") as String)
        isClosingConfirmationEnabled = webApp.isClosingConfirmationEnabled as Boolean

        console.log(webApp.openLink)

    }

    override fun toString(): String {
        return buildString {
            append("colorScheme: ", colorScheme, "\n")
            append("platform: ", platform, "\n")
            append("version: ", version, "\n")
            append("initData: ", initData, "\n")
            append("isExpanded: ", isExpanded, "\n")
            append("viewportHeight: ", viewportHeight, "\n")
            append("viewportStableHeight: ", viewportStableHeight, "\n")
            append("headerColor: ", headerColor, "\n")
            append("backgroundColor: ", backgroundColor, "\n")
            append("isClosingConfirmationEnabled: ", isClosingConfirmationEnabled, "\n")
        }
    }
}
