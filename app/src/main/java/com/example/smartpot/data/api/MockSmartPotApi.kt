package com.example.smartpot.data.api

import kotlinx.coroutines.delay

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

    override suspend fun postDevices(request: DeviceRequest): Device {
        TODO("Not yet implemented")
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
        TODO()
//        delay(delayMs)
//        return DeviceTriggerWateringResponse(
//            "Mock start",
//            10,
//            Timestamp.from(Instant.now()) as Timestamp
//        )
    }

    override suspend fun getWateringSchedules(deviceId: Int): List<WateringScheduleItem> {
        delay(delayMs)
        return schedules.filter { it.value.deviceId == deviceId }.toList().map {
            it.second
        }
    }

    override suspend fun postWateringSchedule(request: WateringScheduleRequest): WateringScheduleItem {
        TODO()

//        delay(delayMs)
//
//        val id = Random.Default.nextInt()
//
//        val item = WateringScheduleItem(
//            id = id,
//            deviceId = request.wateringSchedule.deviceId,
//            dayOfWeek = request.wateringSchedule.dayOfWeek,
//            startTime = request.wateringSchedule.startTime,
//            endTime = request.wateringSchedule.endTime,
//            active = request.wateringSchedule.active,
//            createdAt = TODO(),
//            updatedAt = TODO()
//        )
//
//        schedules[id] = item
//        return item
    }

    override suspend fun getWateringSchedule(scheduleId: Int): WateringScheduleItem {
        val item = schedules[scheduleId]!!
        return item
    }

    override suspend fun putWateringSchedule(scheduleId: Int, request: WateringScheduleRequest): WateringScheduleItem {
        TODO()
    }

    override suspend fun deleteSchedule(scheduleId: Int) {
        delay(delayMs)
        schedules.remove(scheduleId)
    }
}