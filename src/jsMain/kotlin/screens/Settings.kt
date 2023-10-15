package screens

import client
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import models.Group
import models.Profile
import mui.material.Button
import mui.material.Stack
import mui.material.Typography
import react.FC
import react.Props
import web.location.location

external interface SettingsProps : Props {
    var profile: Profile
    var groups: Array<Group>
}


val Settings = FC<SettingsProps> { props ->
    Stack {
        Typography {
            +"Выбранная группа: ${props.groups.first { it.group_id == props.profile.group_selected }.group_name}"
        }
        Button {
            onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                    client.postgrest.from("profiles").update({ set("group_selected", null as Int?) }) {
                        eq("telegram_id", props.profile.telegram_id)
                    }
                    location.reload()

                }

            }
            +"Сменить группу"
        }
    }
}