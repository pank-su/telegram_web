import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import models.Group
import models.Profile
import react.create
import react.dom.client.createRoot
import screens.GeneralScreen
import screens.LoadingScreen
import screens.SelectGroup
import telegram_api.TelegramWebApp
import web.dom.document


val client = createSupabaseClient(
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZlY25sZGp4cHNlcmNleWlpZnd0Iiwicm9sZSI6ImFub24iLCJpYXQiOjE2ODkwMjA5MjAsImV4cCI6MjAwNDU5NjkyMH0.yMRwZhIOlCo7EeTZ0lO5E_HJq-iV-ANZJFiNpwb3aeU",
    supabaseUrl = "https://fecnldjxpserceyiifwt.supabase.co"
) {
    install(Postgrest)
}

enum class UIState {
    Loading, Loaded
}

enum class UserState {
    UnSelectedGroup,
    SelectedGroup
}

fun main() {
    var container = document.createElement("div")
    document.body.appendChild(container)

    val telegramWebApp = TelegramWebApp()
    println(telegramWebApp)


    println(telegramWebApp.initData)


    var state = UIState.Loading
    var userState = UserState.UnSelectedGroup
    var profile: Profile? = null
    var loadedGroups: List<Group> = listOf()


    createRoot(container).render(
        GeneralScreen.create()
    )

}