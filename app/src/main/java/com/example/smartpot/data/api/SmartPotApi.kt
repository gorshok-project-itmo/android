package com.example.smartpot.data.api

import java.sql.Timestamp
import java.time.DayOfWeek
import java.time.LocalTime

data class DeviceTriggerWateringResponse(
    val message: String,
    val duration: Int,
    val startedAt: Timestamp
)

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

data class WateringStatus(
    val deviceName: String,
    val mode: String,
    val waterLevel: Double,
    val humidityThreshold: Double,
    val needsWatering: Boolean
)

data class Auth(
    val email: String,
    val password: String
)

data class User(
    val id: Int,
    val email: String
)

data class AuthStatus(
    val code: Int,
    val message: String,
    val token: String,
    val data: User
)

data class AuthRequest(
    val user: Auth
)

data class AuthResponse(
    val status: AuthStatus
)

data class LogoutResponse(
    val status: Int,
    val message: String
)

data class WateringScheduleRequest(
    val deviceId: Int,
    val dayOfWeek: DayOfWeek,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val active: Boolean
)

data class WateringScheduleItem(
    val id: Int,
    val deviceId: Int,
    val dayOfWeek: DayOfWeek,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val active: Boolean
)

interface SmartPotApi {
    suspend fun postSignup(request: AuthRequest): AuthResponse
    suspend fun postLogin(request: AuthRequest): AuthResponse
    suspend fun deleteLogout(): LogoutResponse
    suspend fun getDevices(): List<Device>
    suspend fun getDevice(deviceId: Int): Device
    suspend fun getDeviceWateringStatus(deviceId: Int): WateringStatus
    suspend fun postDeviceTriggerWatering(deviceId: Int): DeviceTriggerWateringResponse

    suspend fun getWateringSchedules(deviceId: Int): List<WateringScheduleItem>
    suspend fun postWateringSchedule(request: WateringScheduleRequest): WateringScheduleItem
    suspend fun getWateringSchedule(id: Int): WateringScheduleItem
    suspend fun putWateringSchedule(item: WateringScheduleItem): WateringScheduleItem
    suspend fun deleteSchedule(id: Int)
}