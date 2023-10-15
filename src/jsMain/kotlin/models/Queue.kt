package models

import kotlinx.serialization.Serializable


@Serializable
data class Queue (val schedule_id: Int, val profile_id: String, val comment: String?)