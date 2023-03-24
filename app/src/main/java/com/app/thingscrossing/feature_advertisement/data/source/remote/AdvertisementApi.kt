package com.app.thingscrossing.feature_advertisement.data.source.remote

import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import retrofit2.Call
import retrofit2.http.*

interface AdvertisementApi {
    @GET("advertisement/")
    fun getAdvertisementList(): Call<List<Advertisement>>

    @GET("advertisement/{id}")
    fun getAdvertisementById(@Path("id") id: String): Call<Advertisement>

    @POST("advertisement")
    fun insertAdvertisement(@Body advertisement: Advertisement)

    @DELETE("advertisement/{id}")
    fun deleteAdvertisement(@Path("id") id: Int)

    @GET("advertisement/search")
    fun searchAdvertisements(@Query("q") searchValue: String): Call<List<Advertisement>>
}