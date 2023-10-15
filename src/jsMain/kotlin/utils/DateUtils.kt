package utils

import kotlin.js.Date

fun getWeekStart(date: Date): Date {
    val currentDayOfWeek = date.getDay()
    val daysToAdd = if (currentDayOfWeek == 0) -6 else 1 - currentDayOfWeek
    return Date(date.getTime() + daysToAdd * 24 * 60 * 60 * 1000)
}


fun getShortDayName(dayIndex: Int): String {
    val days = arrayOf("Вс", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб")
    return days[dayIndex]
}