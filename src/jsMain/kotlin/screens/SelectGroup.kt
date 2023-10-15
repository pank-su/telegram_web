package screens

import client
import emotion.react.css
import io.github.jan.supabase.postgrest.postgrest
import js.core.jso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import models.Group
import models.Profile
import mui.material.*
import mui.system.responsive
import react.*
import react.dom.html.ReactHTML
import web.cssom.*
import web.location.location

external interface SelectGroupProps : Props {
    var loadedGroups: Array<Group>
    var userState: UserState
}


val SelectGroup = FC<SelectGroupProps> {

    var selectedValue: screens.Group? by useState(null)
    var userState by useState(it.userState)

    Grid {
        css {
            paddingLeft = 12.px
            paddingRight = 12.px
        }
        container = true
        Grid {
            item = true
            xs = 12
            Stack {
                css {
                    justifyContent = JustifyContent.center
                    alignItems = AlignItems.center
                }
                spacing = responsive(2)
                direction = responsive(StackDirection.row)
                ReactHTML.img {
                    src = "logo.svg"
                    css {
                        height = 24.px
                        width = 24.px
                    }
                }
                ReactHTML.p {
                    +"Guapgramm"
                    css {
                        fontFamily = string("Roboto Serif")
                        fontWeight = FontWeight.bold
                        fontSize = 24.px
                    }
                }
            }
        }
        Grid {
            item = true
            xs = 12
            css {
                height = 24.px
            }
        }
        Grid {
            item = true
            xs = 12
            Stack {
                Typography {
                    +"Выберите группу"
                }
                Autocomplete<AutocompleteProps<screens.Group>> {
                    onChange = { event, newValue, _, _ ->
                        selectedValue = newValue
                    }
                    value = selectedValue
                    options = it.loadedGroups.map {
                        toJSO(it)
                    }.toTypedArray()
                    renderInput = { params ->
                        TextField.create {
                            +params
                            label = ReactNode("Группа")
                        }
                    }
                }
            }
        }
        Button {
            onClick = {
                console.log(selectedValue)
                if (selectedValue != null)
                    CoroutineScope(Dispatchers.Main).launch {
                        val telegramId = js("window.Telegram.WebApp.initDataUnsafe.user.id")
                        client.postgrest.from("profiles").update({
                            Profile::group_selected setTo selectedValue!!.group_id
                        }) {
                            Profile::telegram_id eq telegramId.toString()
                        }
                        location.reload()
                    }
            }
            +"Выбрать"
        }
    }
}


external interface Group {
    var group_id: Int
    var label: String
}

fun toJSO(group: Group): screens.Group {
    return jso {
        this.label = group.group_name
        this.group_id = group.group_id
    }
}