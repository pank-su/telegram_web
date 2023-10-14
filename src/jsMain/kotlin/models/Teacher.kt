package models

import kotlinx.serialization.Serializable


@Serializable
data class Teacher(
    val id: Int,
    val last_name: String,
    val first_name: String,
    val second_name: String,
    val email: String?,
    val photo: String
)
