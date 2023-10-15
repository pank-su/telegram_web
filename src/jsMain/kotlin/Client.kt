import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import react.create
import react.dom.client.createRoot
import screens.GeneralScreen
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


fun main() {
    var container = document.createElement("div")
    document.body.appendChild(container)


    createRoot(container).render(
        GeneralScreen.create()
    )

}