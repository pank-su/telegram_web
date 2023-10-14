import csstype.Properties
import emotion.react.css
import mui.material.*
import mui.system.SxProps
import mui.system.Theme
import mui.system.responsive
import mui.system.sx
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.p
import telegram_api.TelegramWebApp
import utils.getShortDayName
import utils.getWeekStart
import web.cssom.*
import kotlin.js.Date

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


external interface WelcomeProps : Props {
    var name: String
    var app: TelegramWebApp
}


val Welcome = FC<WelcomeProps> { props ->
    val name by useState(props.name)
    val app by useState(props.app)

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
            WeekLine()
        }
        Grid {
            item = true
            xs = 12
            Card {
                variant = "outlined"
                CardContent {

                }
            }
        }
    }

}

external interface WeekLineProps : Props {
}

val WeekLine = FC<WeekLineProps> {
    var selectedDate by useState(Date())
    val today by useState(Date())
    Stack {
        direction = responsive(StackDirection.row)
        css {
            justifyContent = JustifyContent.spaceBetween
            //marginTop = 10.px
            //marginBottom = 10.px
            marginLeft = 7.px
            marginRight = 7.px
        }
        // Получение текцщей недели
        val weekStart = getWeekStart(selectedDate)
        (0..<7).map {
            val day = Date(weekStart.getTime() + it * 24 * 60 * 60 * 1000)
            val dayOfMonth = day.getDate()

            Box {
                onClick = {
                    selectedDate = day
                }
                sx {
                    width = 60.px
                    height = 60.px
                    borderRadius = 100.px
                    border = Border(1.px, LineStyle.solid)
                    borderColor = Color("transparent")
                    if (day.getDate() == today.getDate()){
                        backgroundColor = Color("primary.main")
                        color = Color("white")
                    } else if (selectedDate.getDate() == day.getDate()) {
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
                Typography{
                    css {
                        fontSize = 16.px
                        if (day.getDate() == selectedDate.getDate()){
                            color = Color("primary.contrastText")
                        } else if (day.getDate() == today.getDate()){
                            color =  Color("primary.contrastText")
                        }
                    }
                    +getShortDayName(day.getDay())
                }
                Typography{
                    css {
                        fontSize = 16.px
                    }
                    +dayOfMonth.toString()
                }

            }
        }
    }
}