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
import mui.material.*
import mui.system.responsive
import mui.system.sx
import react.*
import web.cssom.Color
import web.cssom.px
import kotlin.js.Date

external interface ScheduleCardsProps : Props {
    var selectedDate: Date
    var groupId: Int
}


val ScheduleCards = FC<ScheduleCardsProps> {
    var schedule: List<DayRow> by useState(listOf())

    useEffect(it.selectedDate) {
        CoroutineScope(Dispatchers.Main).launch {
            schedule = client.postgrest.rpc(
                "get_day_schedule",
                GetDayScheduleParams(it.selectedDate.getDay(), group_id_ = it.groupId, is_numerator_ = false)
            ).decodeList<DayRow>()
            console.log(schedule)
        }

    }

    Stack {
        direction = responsive(StackDirection.column)
        css {
            marginTop = 10.px
        }
        for (row in schedule) {
            Card {

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
                            }
                            label = ReactNode(row.cabinet_number)
                        }
                }
            }
        }


    }
}




