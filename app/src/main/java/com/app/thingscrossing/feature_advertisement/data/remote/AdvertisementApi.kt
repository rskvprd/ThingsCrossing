package com.app.thingscrossing.feature_advertisement.data.remote

import com.app.thingscrossing.feature_advertisement.data.remote.dto.AdvertisementDto
import retrofit2.http.*

interface AdvertisementApi {
    @GET("advertisement/")
    suspend fun getAdvertisementList(): List<AdvertisementDto>

    @GET("advertisement/{id}")
    suspend fun getAdvertisementById(@Path("id") id: String): AdvertisementDto

    @POST("advertisement")
    suspend fun insertAdvertisement(@Body advertisement: AdvertisementDto)

    @DELETE("advertisement/{id}")
    suspend fun deleteAdvertisement(@Path("id") id: Int)

    @GET("advertisement/search")
    suspend fun searchAdvertisements(@Query("q") searchValue: String): List<AdvertisementDto>
}