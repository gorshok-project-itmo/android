package com.example.smartpot.data.mock

import com.example.smartpot.data.api.AuthRequest
import com.example.smartpot.data.api.AuthResponse
import com.example.smartpot.data.api.AuthStatus
import com.example.smartpot.data.api.Device
import com.example.smartpot.data.api.DeviceApi
import com.example.smartpot.data.api.DeviceTriggerWateringResponse
import com.example.smartpot.data.api.LogoutResponse
import com.example.smartpot.data.api.User
import com.example.smartpot.data.api.WateringScheduleItem
import com.example.smartpot.data.api.WateringScheduleRequest
import com.example.smartpot.data.api.WateringStatus
import kotlinx.coroutines.delay
import java.sql.Timestamp
import java.time.Instant
import kotlin.random.Random

class MockDeviceApi(
    private val delayMs: Long = 200L
) : DeviceApi {
    private val devices = mutableMapOf<Int, Device>()
    private val schedules = mutableMapOf<Int, WateringScheduleItem>()
    private var token: String? = null

    override suspend fun postSignup(request: AuthRequest): AuthResponse {
        delay(delayMs)
        val token = "1234567890"

        val resp = AuthResponse(
            AuthStatus(
                200,
                "success",
                token,
                User(
                    1,
                    "test@test.com"
                )
            )
        )

        this.token = token

        return resp
    }

    override suspend fun postLogin(request: AuthRequest): AuthResponse {
        delay(delayMs)
        val token = "1234567890"

        val resp = AuthResponse(
            AuthStatus(
                200,
                "success",
                token,
                User(
                    1,
                    "test@test.com"
                )
            )
        )

        this.token = token

        return resp
    }

    override suspend fun deleteLogout(): LogoutResponse {
        delay(delayMs)
        return LogoutResponse(
            200,
            "success"
        )
    }

    override suspend fun getDevices(): List<Device> {
        delay(delayMs)
        return this.devices.values.toList()
    }

    override suspend fun getDevice(deviceId: Int): Device {
        delay(delayMs)
        return this.devices[deviceId]!!
    }

    override suspend fun getDeviceWateringStatus(deviceId: Int): WateringStatus {
        delay(delayMs)
        val device = devices[deviceId]!!
        return WateringStatus(
            device.name,
            device.mode,
            device.waterLevel,
            device.humidityThreshold,
            false
        )
    }

    override suspend fun postDeviceTriggerWatering(deviceId: Int): DeviceTriggerWateringResponse {
        delay(delayMs)
        return DeviceTriggerWateringResponse(
            "Mock start",
            10,
            Timestamp.from(Instant.now()) as Timestamp
        )
    }

    override suspend fun getWateringSchedules(deviceId: Int): List<WateringScheduleItem> {
        delay(delayMs)
        return schedules.filter { it.value.deviceId == deviceId }.toList().map {
            it.second
        }
    }

    override suspend fun postWateringSchedule(request: WateringScheduleRequest): WateringScheduleItem {
        delay(delayMs)

        val item = WateringScheduleItem(
            id = Random.nextInt(),
            deviceId = request.deviceId,
            dayOfWeek = request.dayOfWeek,
            startTime = request.startTime,
            endTime = request.endTime,
            active = request.active
        )

        schedules.getOrPut(item.deviceId) { item }
        return item
    }

    override suspend fun getWateringSchedule(id: Int): WateringScheduleItem {
        val item = schedules.filter {
            it.value.id == id
        }[0]!!

        return item
    }

    override suspend fun putWateringSchedule(item: WateringScheduleItem): WateringScheduleItem {
        delay(delayMs)
        schedules.getOrPut(item.deviceId) { item }
        return item
    }

    override suspend fun deleteSchedule(id: Int) {
        delay(delayMs)
        schedules.remove(id)
    }
}