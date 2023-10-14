package models

import kotlinx.serialization.Serializable

@Serializable
data class GetDayScheduleParams(
    val day_id_: Int,
    val group_id_: Int,
    val is_numerator_: Boolean
)
