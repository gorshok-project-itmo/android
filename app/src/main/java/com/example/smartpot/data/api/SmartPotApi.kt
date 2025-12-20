package com.example.smartpot.data.api

import com.example.smartpot.util.DayOfWeekSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import retrofit2.Response
import java.time.DayOfWeek
import java.time.LocalTime

@Serializable
data class DeviceTriggerWateringResponse(
    val message: String,
    val duration: Int,
    val startedAt: String,
    val estimatedFinish: String,
    val waterLevelBefore: Double,
    val waterLevelAfter: Double,
)

@Serializable
data class DeviceRequest(
    val name: String,
    val mode: String,
    val intervalHours: Int,
    val durationMinutes: Int,
    val humidityThreshold: Double,
    val waterLevel: Double
)

@Serializable
data class Device(
    val id: Int,
    val name: String,
    val mode: String,
    val intervalHours: Int,
    val durationMinutes: Int,
    val humidityThreshold: Double,
    val waterLevel: Double,
    val userId: Int,
    val createdAt: String,
    val updatedAt: String
)

@Serializable
data class WateringStatus(
    val deviceName: String,
    val mode: String,
    val waterLevel: Double,
    val humidityThreshold: Double,
    val needsWatering: Boolean
)

@Serializable
data class UserAuth(
    val email: String,
    val password: String
)

@Serializable
data class UserData(
    val id: Int,
    val email: String
)

@Serializable
data class AuthStatus(
    val code: Int,
    val message: String,
    val token: String,
    val data: UserData
)

@Serializable
data class AuthRequest(
    val user: UserAuth
)

@Serializable
data class AuthResponse(
    val status: AuthStatus
)

@Serializable
data class LogoutResponse(
    val status: Int,
    val message: String
)

@Serializable
data class WateringScheduleRequestData(
    val deviceId: Int,
    @Serializable(with = DayOfWeekSerializer::class) val dayOfWeek: DayOfWeek,
    @Contextual val startTime: LocalTime,
    @Contextual val endTime: LocalTime,
    val active: Boolean
)

@Serializable
data class WateringScheduleRequest(
    val wateringSchedule: WateringScheduleRequestData
)

@Serializable
data class WateringScheduleItem(
    val id: Int,
    val deviceId: Int,
    @Serializable(with = DayOfWeekSerializer::class) val dayOfWeek: DayOfWeek,
    @Contextual val startTime: LocalTime,
    @Contextual val endTime: LocalTime,
    val active: Boolean,
    val createdAt: String,
    val updatedAt: String
)

interface SmartPotApi {
    suspend fun postSignup(request: AuthRequest): Response<AuthResponse>
    suspend fun postLogin(request: AuthRequest): Response<AuthResponse>
    suspend fun deleteLogout(): Response<LogoutResponse>
    suspend fun postDevices(request: DeviceRequest): Response<Device>
    suspend fun getDevices(): Response<List<Device>>
    suspend fun getDevice(deviceId: Int): Response<Device>
    suspend fun getDeviceWateringStatus(deviceId: Int): Response<WateringStatus>
    suspend fun postDeviceTriggerWatering(deviceId: Int): Response<DeviceTriggerWateringResponse>

    suspend fun getWateringSchedules(deviceId: Int): Response<List<WateringScheduleItem>>
    suspend fun postWateringSchedule(request: WateringScheduleRequest): Response<WateringScheduleItem>
    suspend fun getWateringSchedule(scheduleId: Int): Response<WateringScheduleItem>
    suspend fun putWateringSchedule(scheduleId: Int, request: WateringScheduleRequest): Response<WateringScheduleItem>
    suspend fun deleteSchedule(scheduleId: Int): Response<Unit>
}