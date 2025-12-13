package com.example.smartpot.util

import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import kotlin.math.abs

fun formatDuration(duration: Duration): String {
    val seconds = abs(duration.seconds)
    val mins = seconds / 60
    val secs = seconds % 60
    return String.format(Locale.getDefault(), "%02d:%02d", mins, secs)
}

fun durationBetween(start: LocalTime, end: LocalTime): Duration {
    return Duration.ofSeconds((end.toSecondOfDay() - start.toSecondOfDay()).toLong())
}

fun LocalTime.customFormat(): String = this.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))

val days = mapOf(
    DayOfWeek.MONDAY to "Понедельник",
    DayOfWeek.TUESDAY to "Вторник",
    DayOfWeek.WEDNESDAY to "Среда",
    DayOfWeek.THURSDAY to "Четверг",
    DayOfWeek.FRIDAY to "Пятница",
    DayOfWeek.SATURDAY to "Суббота",
    DayOfWeek.SUNDAY to "Воскресенье"
)