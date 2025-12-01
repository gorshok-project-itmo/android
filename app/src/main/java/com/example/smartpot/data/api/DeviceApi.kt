package com.example.smartpot.data.api

import java.time.DayOfWeek
import java.time.Duration
import java.util.UUID
import java.time.LocalTime

data class DeviceStartRequest(val deviceId: String)
data class DeviceStartResponse(val deviceId: String, val success: Boolean, val message: String)

data class DeviceStopRequest(val deviceId: String)
data class DeviceStopResponse(val deviceId: String, val success: Boolean, val message: String)

data class ScheduleItem(
    val id: String = UUID.randomUUID().toString(),
    val deviceId: String,
    val dayOfWeek: DayOfWeek,
    val time: LocalTime,
    val duration: Duration
)

data class ScheduleResponse(val success: Boolean, val message: String, val schedule: List<ScheduleItem> = emptyList())

interface DeviceApi {
    suspend fun postStart(request: DeviceStartRequest): DeviceStartResponse
    suspend fun postStop(request: DeviceStopRequest): DeviceStopResponse

    suspend fun getSchedules(deviceId: String): ScheduleResponse
    suspend fun addSchedule(item: ScheduleItem): ScheduleResponse
    suspend fun updateSchedule(item: ScheduleItem): ScheduleResponse
    suspend fun deleteSchedule(item: ScheduleItem): ScheduleResponse
}