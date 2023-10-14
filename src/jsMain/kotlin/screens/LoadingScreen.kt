package screens

import mui.material.CircularProgress
import mui.system.Box
import mui.system.sx
import react.FC
import react.Props
import react.useState
import web.cssom.Auto
import web.cssom.px
import web.cssom.vh
import web.cssom.vw

external interface LoadingScreenProps : Props {

}

val LoadingScreen = FC<LoadingScreenProps> {


    Box {
        sx {
            width = 100.vw
            height = 100.vh
        }
        CircularProgress {
            sx {
                margin = Auto.auto
                height = 50.px
                width = 50.px
            }
        }
    }
}
