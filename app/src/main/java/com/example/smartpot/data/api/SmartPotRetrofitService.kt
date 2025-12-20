package com.example.smartpot.data.api

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SmartPotRetrofitService {
    @POST("signup")
    suspend fun postSignup(@Body request: AuthRequest): AuthResponse

    @POST("login")
    suspend fun postLogin(@Body request: AuthRequest): AuthResponse

    @DELETE("logout")
    suspend fun deleteLogout(): LogoutResponse

    @POST("devices")
    suspend fun postDevices(@Body request: DeviceRequest): Device

    @GET("devices")
    suspend fun getDevices(): List<Device>

    @GET("devices/{id}")
    suspend fun getDevice(@Path("id") deviceId: Int): Device

    @GET("devices/{id}/watering_status")
    suspend fun getDeviceWateringStatus(@Path("id") deviceId: Int): WateringStatus

    @POST("devices/{id}/trigger_watering")
    suspend fun postDeviceTriggerWatering(@Path("id") deviceId: Int): DeviceTriggerWateringResponse

    @GET("devices/{id}/watering_schedules")
    suspend fun getWateringSchedules(@Path("id") deviceId: Int): List<WateringScheduleItem>

    @POST("devices/{id}/watering_schedules")
    suspend fun postWateringSchedule(@Path("id") deviceId: Int, @Body request: WateringScheduleRequest): WateringScheduleItem

    @GET("watering_schedules/{id}")
    suspend fun getWateringSchedule(@Path("id") scheduleId: Int): WateringScheduleItem

    @PUT("watering_schedules/{id}")
    suspend fun putWateringSchedule(@Path("id") scheduleId: Int, @Body request: WateringScheduleRequest): WateringScheduleItem

    @DELETE("watering_schedules/{id}")
    suspend fun deleteSchedule(@Path("id") scheduleId: Int)
}
