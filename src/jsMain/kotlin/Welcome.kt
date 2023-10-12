import emotion.react.css
import mui.material.Button
import mui.material.ButtonVariant
import react.FC
import react.Props
import react.PropsWithChildren
import react.useState
import web.cssom.px


external interface WelcomeProps : Props {
    var name: String
    var app: TelegramWebApp
}


val Welcome = FC<WelcomeProps> { props ->
    val name by useState(props.name)
    val app by useState(props.app)


   Button {
        variant = ButtonVariant.outlined
        +name
        css {
            height = 100.px
        }
       onClick = {
           app.close()
       }
    }

}