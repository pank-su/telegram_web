package screens

import UserState
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

external interface GeneralScreenProps : Props {

}

val GeneralScreen = FC<GeneralScreenProps> {
    var uiState by useState(UIState.Loading)
    var userState by useState(UserState.UnSelectedGroup)
    var groups by useState(listOf<Group>())
    var profile by useState<Profile?>(null)

    useEffect {
        CoroutineScope(Dispatchers.Main).launch {
            val telegramId = js("window.Telegram.WebApp.initDataUnsafe.user.id")
            profile = client.postgrest.from("profiles").select {
                eq("telegram_id", telegramId.toString())
            }.decodeSingle<Profile>()
            if (profile!!.group_selected == null) {

                groups = client.postgrest.from("group").select().decodeList()
            } else{
                userState = UserState.SelectedGroup
            }

            uiState = UIState.Loaded
        }
    }

    if (uiState === UIState.Loading) {
        LoadingScreen {

        }
    } else {
        if (userState == UserState.UnSelectedGroup)
            SelectGroup {
                loadedGroups = groups.toTypedArray()
                this.userState = userState
            }
        else{
            Schedule{
                this.profile = profile!!
            }
        }
    }
}