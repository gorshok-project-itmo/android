package com.example.smartpot.data.api

import kotlinx.coroutines.delay
import java.sql.Timestamp
import java.time.Instant
import kotlin.random.Random

class MockSmartPotApi(
    private val delayMs: Long = 200L
) : SmartPotApi {
    private val devices = mutableMapOf<Int, Device>(1 to Device(
        id = 1,
        name = "MyDevice1",
        mode = "schedule",
        intervalHours = 5,
        durationMinutes = 30,
        humidityThreshold = 0.3,
        waterLevel = 1.0,
        userId = 99,
        createdAt = "",
        updatedAt = ""
    ))
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

    override suspend fun getDevice(deviceId: Int): Device? {
        delay(delayMs)
        return this.devices[deviceId]
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

    override suspend fun putDevice(device: Device): Device {
        delay(delayMs)

        this.devices[device.id] = device.copy()

        return this.devices[device.id]!!
    }

    override suspend fun getWateringSchedules(deviceId: Int): List<WateringScheduleItem> {
        delay(delayMs)
        return schedules.filter { it.value.deviceId == deviceId }.toList().map {
            it.second
        }
    }

    override suspend fun postWateringSchedule(request: WateringScheduleRequest): WateringScheduleItem {
        delay(delayMs)

        val id = Random.Default.nextInt()

        val item = WateringScheduleItem(
            id = id,
            deviceId = request.deviceId,
            dayOfWeek = request.dayOfWeek,
            startTime = request.startTime,
            endTime = request.endTime,
            active = request.active
        )

        schedules[id] = item
        return item
    }

    override suspend fun getWateringSchedule(id: Int): WateringScheduleItem {
        val item = schedules[id]!!
        return item
    }

    override suspend fun putWateringSchedule(item: WateringScheduleItem): WateringScheduleItem {
        delay(delayMs)
        schedules[item.id] = item
        return item
    }

    override suspend fun deleteSchedule(id: Int) {
        delay(delayMs)
        schedules.remove(id)
    }
}