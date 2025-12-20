package com.example.smartpot.data.api

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
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
data class Auth(
    val email: String,
    val password: String
)

@Serializable
data class User(
    val id: Int,
    val email: String
)

@Serializable
data class AuthStatus(
    val code: Int,
    val message: String,
    val token: String,
    val data: User
)

@Serializable
data class AuthRequest(
    val user: Auth
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
    val dayOfWeek: DayOfWeek,
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
    val dayOfWeek: DayOfWeek,
    @Contextual val startTime: LocalTime,
    @Contextual val endTime: LocalTime,
    val active: Boolean,
    val createdAt: String,
    val updatedAt: String
)

interface SmartPotApi {
    suspend fun postSignup(request: AuthRequest): AuthResponse
    suspend fun postLogin(request: AuthRequest): AuthResponse
    suspend fun deleteLogout(): LogoutResponse
    suspend fun postDevices(request: DeviceRequest): Device
    suspend fun getDevices(): List<Device>
    suspend fun getDevice(deviceId: Int): Device?
    suspend fun getDeviceWateringStatus(deviceId: Int): WateringStatus
    suspend fun postDeviceTriggerWatering(deviceId: Int): DeviceTriggerWateringResponse

    suspend fun getWateringSchedules(deviceId: Int): List<WateringScheduleItem>
    suspend fun postWateringSchedule(request: WateringScheduleRequest): WateringScheduleItem
    suspend fun getWateringSchedule(scheduleId: Int): WateringScheduleItem
    suspend fun putWateringSchedule(scheduleId: Int, request: WateringScheduleRequest): WateringScheduleItem
    suspend fun deleteSchedule(scheduleId: Int)
}