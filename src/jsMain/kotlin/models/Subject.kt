package models

import kotlinx.serialization.Serializable

@Serializable
data class Subject(val id: Int, val subject_name: String)
