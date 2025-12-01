package com.example.smartpot.data.api

data class DeviceStartRequest(val deviceId: String)
data class DeviceStartResponse(val deviceId: String, val success: Boolean, val message: String)

data class DeviceStopRequest(val deviceId: String)
data class DeviceStopResponse(val deviceId: String, val success: Boolean, val message: String)

interface DeviceApi {
    suspend fun postStart(request: DeviceStartRequest): DeviceStartResponse
    suspend fun postStop(request: DeviceStopRequest): DeviceStopResponse
}