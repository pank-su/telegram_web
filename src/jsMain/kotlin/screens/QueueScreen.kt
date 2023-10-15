package screens

import client
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import models.Profile
import models.Queue
import mui.material.*
import react.FC
import react.Props
import react.ReactNode
import react.useEffect

external interface QueueScreenProps: Props{
    var queue: List<String>
    var scheduleId: Int
}

val QueueScreen = FC<QueueScreenProps>{
    props ->

    var profiles: List<Profile> = listOf()

    useEffect(props.queue) {
        CoroutineScope(Dispatchers.Main).launch {
            for (id in props.queue){
                profiles = client.postgrest.from("profiles").select{
                    eq("telegram_id", id)
                }.decodeList<Profile>()
                console.log(profiles)
            }
        }

    }

    Stack{
        for (profile in profiles) {
            Card {
                CardHeader {
                    avatar = ReactNode(profile.photo_telegram)
                    title = ReactNode(profile.fio)
                }
            }
        }
        Button{
            onClick={
                CoroutineScope(Dispatchers.Main).launch {
                    val telegramId = js("window.Telegram.WebApp.initDataUnsafe.user.id")
                    client.postgrest.from("queue").insert(
                        Queue(props.scheduleId, telegramId.toString(), null)
                    )
                }
            }
            +"Войти в очередь"
        }
    }
}