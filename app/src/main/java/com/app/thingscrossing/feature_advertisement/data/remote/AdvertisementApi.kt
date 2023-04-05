package com.app.thingscrossing.feature_advertisement.data.remote

import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.model.ImageModel
import okhttp3.MultipartBody
import retrofit2.http.*

interface AdvertisementApi {
    @GET("advertisement/")
    suspend fun getAdvertisementList(): List<Advertisement>

    @GET("advertisement/{id}/")
    suspend fun getAdvertisementById(@Path("id") id: String): Advertisement

    @POST("advertisement/")
    suspend fun insertAdvertisement(@Body advertisement: Advertisement)

    @DELETE("advertisement/{id}/")
    suspend fun deleteAdvertisement(@Path("id") id: Int)

    @GET("advertisement/search/")
    suspend fun searchAdvertisements(@Query("q") searchValue: String): List<Advertisement>

    @Multipart
    @POST("pictures/")
    suspend fun uploadImage(@Part image: MultipartBody.Part): ImageModel

    @DELETE("pictures/{id}/")
    suspend fun deletePicture(@Path("id") url: Int)
}