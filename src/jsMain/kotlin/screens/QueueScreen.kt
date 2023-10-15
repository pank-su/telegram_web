package screens

import client
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import models.Profile
import models.Queue
import mui.material.*
import mui.system.sx
import react.*
import web.cssom.px

external interface QueueScreenProps : Props {
    var queue: List<String>
    var scheduleId: Int
    var profile: Profile
}

val QueueScreen = FC<QueueScreenProps> { props ->

    var profiles: MutableList<Profile> by useState(mutableListOf())
    var state: UIState by useState(UIState.Loading)

    useEffect(props.queue) {
        profiles.clear()
        CoroutineScope(Dispatchers.Main).launch {
            for (id in props.queue) {
                profiles.add(client.postgrest.from("profiles").select {
                    eq("telegram_id", id)
                }.decodeSingle())
            }
            state = UIState.Loaded
        }
    }
    if (state == UIState.Loaded)
        Stack {
            for (profile in profiles) {
                Card {
                    CardHeader {
                        avatar = Avatar.create {
                            src = profile.photo_telegram ?: "U"
                        }
                        title = ReactNode(profile.fio)
                    }
                }
            }
            if (!profiles.contains(props.profile))
                Button {
                    onClick = {
                        CoroutineScope(Dispatchers.Main).launch {
                            client.postgrest.from("queue").insert(
                                Queue(props.scheduleId, props.profile.telegram_id, null)
                            )
                            profiles.add(props.profile)
                            profiles = profiles.toMutableList()
                        }
                    }
                    +"Войти в очередь"
                }
            else {
                Button {
                    onClick = {
                        CoroutineScope(Dispatchers.Main).launch {

                            client.postgrest.from("queue").delete {
                                eq("schedule_id", props.scheduleId)
                                eq("profile_id", props.profile.telegram_id)
                            }
                            profiles.remove(props.profile)
                            profiles = profiles.toMutableList()
                        }
                    }
                    +"Выйти из очереди"
                }
            }
        }
    else
        CircularProgress {
            sx {
                width = 50.px
                height = 50.px
            }
        }
}