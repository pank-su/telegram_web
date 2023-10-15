package screens

import client
import emotion.react.css
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import models.DayRow
import models.GetDayScheduleParams
import models.Profile
import mui.material.*
import mui.system.responsive
import mui.system.sx
import react.*
import web.cssom.Color
import web.cssom.px
import kotlin.js.Date

external interface ScheduleCardsProps : Props {
    var setUserState: StateSetter<UserState>
    var isNumerator: Boolean
    var selectedDate: Date
    var groupId: Int
    var profile: Profile
}


val ScheduleCards = FC<ScheduleCardsProps> {
    var schedule: List<DayRow> by useState(listOf())
    var queue: List<String> by useState(listOf())
    var scheduleId by useState(0)

    useEffect(it.selectedDate) {
        CoroutineScope(Dispatchers.Main).launch {
            schedule = client.postgrest.rpc(
                "get_day_schedule",
                GetDayScheduleParams(
                    it.selectedDate.getDay(),
                    group_id_ = it.groupId,
                    is_numerator_ = if (it.groupId < 44) !it.isNumerator else it.isNumerator
                )
            ).decodeList<DayRow>().sortedBy {
                Regex("\\d+").findAll(it.time_str).toList()[0].value.toInt()
            }
            console.log(schedule)
        }

    }

    var isOpen by useState(false)


    Stack {
        direction = responsive(StackDirection.column)
        css {
            marginTop = 10.px
        }
        for (row in schedule) {
            Card {
                onClick = {
                    scheduleId = row.schedule_id
                    queue = row.queue ?: listOf()
                    isOpen = true
                }
                variant = "outlined"
                CardHeader {
                    action = ReactNode(row.time_str)
                }
                CardContent {
                    Typography {
                        +row.subject.subject_name
                    }
                }
                CardActions {
                    sx {
                        justifyContent = web.cssom.JustifyContent.right
                    }
                    if (row.queue != null) {
                        Chip {
                            label = ReactNode("\uD83E\uDDCD ${row.queue.size}")
                        }
                    }
                    if (row.teacher != null)
                        Chip {
                            avatar = Avatar.create {
                                src = row.teacher.photo
                            }
                            sx {
                                backgroundColor = Color("primary.main")
                                color = Color("white")
                            }
                            label = ReactNode(
                                row.teacher.last_name
                                        + " "
                                        + row.teacher.first_name[0]
                                        + "."
                                        + row.teacher.second_name[0]
                                        + "."
                            )
                        }
                    if (row.cabinet_number != null)
                        Chip {
                            sx {
                                backgroundColor = Color("secondary.main")
                                color = Color("white")
                            }
                            label = ReactNode(row.cabinet_number)
                        }
                }
            }
        }


    }

    Dialog {
        open = isOpen
        onClose = { _, _ -> isOpen = false }
        DialogTitle {
            +"Очередь"
        }
        DialogContent {
            QueueScreen {
                this.queue = queue
                this.scheduleId = scheduleId
                this.profile = it.profile
            }
            DialogActions {
                Button {
                    onClick = { isOpen = false }
                    +"Отмена"
                }

            }
        }
    }
}




