package com.app.thingscrossing.feature_advertisement.domain.repository

import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.model.ImageModel
import okhttp3.MultipartBody

typealias Image = ImageModel

interface AdvertisementRepository {
    suspend fun getAdvertisementList(): List<Advertisement>

    suspend fun getAdvertisementById(id: Int): Advertisement

    suspend fun deleteAdvertisement(advertisement: Advertisement)

    suspend fun insertAdvertisement(advertisement: Advertisement)

    suspend fun searchAdvertisements(searchValue: String): List<Advertisement>

    suspend fun deleteImage(image: ImageModel)

    suspend fun uploadImage(file: MultipartBody.Part): ImageModel
}