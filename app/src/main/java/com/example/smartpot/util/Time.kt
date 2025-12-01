package com.example.smartpot.util

import java.time.Duration
import java.util.Locale
import kotlin.math.abs

fun formatDuration(duration: Duration): String {
    val seconds = abs(duration.seconds)
    val mins = seconds / 60
    val secs = seconds % 60
    return String.format(Locale.getDefault(), "%02d:%02d", mins, secs)
}