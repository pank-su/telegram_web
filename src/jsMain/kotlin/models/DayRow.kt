package models

import kotlinx.serialization.Serializable

@Serializable
data class DayRow(
    val time_str: String,
    val subject: Subject,
    val teacher: Teacher?,
    val cabinet_number: String?,
    val queue: List<String>?,
    val schedule_id: Int
)
