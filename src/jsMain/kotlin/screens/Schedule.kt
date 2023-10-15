package screens

import emotion.react.css
import models.Group
import models.Profile
import mui.icons.material.ArrowBackIos
import mui.icons.material.ArrowForwardIos
import mui.icons.material.Settings
import mui.material.*
import mui.system.responsive
import mui.system.sx
import react.*
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.p
import utils.getShortDayName
import utils.getWeekStart
import web.cssom.*
import kotlin.js.Date
import kotlin.math.ceil
import kotlin.math.floor

inline var GridProps.xs: Int
    get() = TODO("Prop is write-only!")
    set(value) {
        asDynamic().xs = value
    }


inline var CardProps.variant: String
    get() = TODO("Prop is write-only")
    set(value) {
        asDynamic().variant = value
    }


external interface ScheduleProps : Props {
    var setUserState: StateSetter<UserState>
    var profile: Profile
    var groupsLoaded: Array<Group>
}


val Schedule = FC<ScheduleProps> { props ->
    val profile by useState(props.profile)
    val (selectedDate, setSelectedDate) = useState(Date())
    val isNumerator = useMemo(selectedDate) {
        val days = floor(
            (selectedDate.getTime() - Date(selectedDate.getFullYear(), 0, 1).getTime()) /
                    (24 * 60 * 60 * 1000)
        )

        ceil(days / 7f)

        return@useMemo ceil(days / 7f).toInt() % 2 == 0
    }


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
                img {
                    src = "logo.svg"
                    css {
                        height = 24.px
                        width = 24.px
                    }
                }
                p {
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
            css {
                backgroundColor = Color("#E9E7EC")
                borderRadius = 50.px
            }
            WeekLine {
                this.selectedDate = selectedDate
                this.setSelectedDate = setSelectedDate
            }
        }
        Grid {
            item = true
            xs = 12
            Typography {
                +"Расписание группы ${props.groupsLoaded.first { g -> g.group_id == profile.group_selected }.group_name} (${if (isNumerator) "чётная" else "нечётная"} неделя)"
            }
        }
        Grid {
            item = true
            xs = 12
            ScheduleCards {
                this.selectedDate = selectedDate
                this.groupId = profile.group_selected!!
                this.isNumerator = isNumerator
                this.setUserState = props.setUserState
                this.profile = props.profile
            }
        }
        Grid {
            item = true
            xs = 12
            IconButton {
                onClick = {
                    props.setUserState(UserState.Settings())
                }
                Settings {

                }
            }
        }
    }

}

external interface WeekLineProps : Props {
    var selectedDate: Date
    var setSelectedDate: StateSetter<Date>
}

val WeekLine = FC<WeekLineProps> { props ->
    var selectedDate = props.selectedDate


    val today by useState(Date())
    Stack {
        direction = responsive(StackDirection.row)
        css {
            justifyContent = JustifyContent.spaceBetween
            //marginTop = 10.px
            //marginBottom = 10.px
        }
        var weekStart by useState(getWeekStart(selectedDate))
        IconButton {
            onClick = {
                weekStart = Date(weekStart.getTime() - 7 * 24 * 60 * 60 * 1000)
            }
            ArrowBackIos {}
        }
        // Получение текущей недели

        (0..<7).map {
            val day = Date(weekStart.getTime() + it * 24 * 60 * 60 * 1000)
            val dayOfMonth = day.getDate()

            Box {
                onClick = {

                    props.setSelectedDate(day)
                    //selectedDate =
                }
                sx {
                    width = 60.px
                    height = 60.px
                    borderRadius = 100.px
                    border = Border(1.px, LineStyle.solid)
                    borderColor = Color("transparent")
                    if (day.getDate() == today.getDate()) {
                        backgroundColor = Color("primary.main")
                        color = Color("white")
                    } else if (selectedDate.getTime() == day.getTime()) {
                        // backgroundColor = Color("primary.main")
                        borderColor = Color("primary.main")
                        color = Color("primary.main")
                    }

                }
                css {
                    display = Display.flex
                    flexDirection = FlexDirection.column
                    flexWrap = FlexWrap.nowrap
                    alignItems = AlignItems.center
                    justifyContent = JustifyContent.center

                }
                Typography {
                    css {
                        fontSize = 12.px
                    }
                    +getShortDayName(day.getDay())
                }
                Typography {
                    css {
                        fontSize = 16.px
                    }
                    +dayOfMonth.toString()
                }

            }
        }
        IconButton {
            onClick = {
                weekStart = Date(weekStart.getTime() + 7 * 24 * 60 * 60 * 1000)
            }
            ArrowForwardIos {

            }
        }
    }
}