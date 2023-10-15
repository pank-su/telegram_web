package screens

import client
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import models.Group
import models.Profile
import react.FC
import react.Props
import react.useEffect
import react.useState


enum class UIState {
    Loading, Loaded
}


sealed class UserState {
    class UnSelectedGroup : UserState()
    class SelectedGroup : UserState()
    class Settings : UserState()
    class Queue(val scheduleId: Int, val queue: List<String>) : UserState()
}

external interface GeneralScreenProps : Props

val GeneralScreen = FC<GeneralScreenProps> {
    var uiState by useState(UIState.Loading)
    var (userState, setUserState) = useState<UserState>(UserState.UnSelectedGroup())
    var groups by useState(listOf<Group>())
    var profile by useState<Profile?>(null)

    useEffect {
        if (uiState == UIState.Loaded)
            return@useEffect
        CoroutineScope(Dispatchers.Main).launch {
            val telegramId = js("window.Telegram.WebApp.initDataUnsafe.user.id")
            profile = client.postgrest.from("profiles").select {
                eq("telegram_id", telegramId.toString())
            }.decodeSingle<Profile>()
            groups = client.postgrest.from("group").select().decodeList()
            if (profile!!.group_selected != null) {
                setUserState(UserState.SelectedGroup())
            }

            uiState = UIState.Loaded
        }
    }

    if (uiState === UIState.Loading) {
        LoadingScreen {
        }
    } else {
        if (userState is UserState.UnSelectedGroup)
            SelectGroup {
                loadedGroups = groups.toTypedArray()
                this.userState = userState
            }
        else if (userState is UserState.Settings) {
            Settings {
                this.profile = profile!!
                this.groups = groups.toTypedArray()
            }
        } else {
            Schedule {
                this.profile = profile!!
                this.groupsLoaded = groups.toTypedArray()
                this.setUserState = setUserState
            }
        }
    }
}