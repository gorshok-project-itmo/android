package com.example.smartpot.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
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


object LocalTimeAsHourMinuteSerializer : KSerializer<LocalTime> {
    private val fmt: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("LocalTimeAsHourMinute", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalTime) {
        val text = value.format(fmt)
        encoder.encodeString(text)
    }

    override fun deserialize(decoder: Decoder): LocalTime {
        val text = decoder.decodeString()
        val t = LocalTime.parse(text, fmt)

        return t
    }
}

object DayOfWeekSerializer : KSerializer<DayOfWeek> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("DayOfWeek", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: DayOfWeek) {
        val text = value.name.lowercase()
        encoder.encodeString(text)
    }

    override fun deserialize(decoder: Decoder): DayOfWeek {
        val text = decoder.decodeString()
        return DayOfWeek.valueOf(text.uppercase())
    }
}