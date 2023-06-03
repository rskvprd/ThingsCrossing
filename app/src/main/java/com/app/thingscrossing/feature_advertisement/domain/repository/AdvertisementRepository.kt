package com.app.thingscrossing.feature_advertisement.domain.repository

import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.model.ImageModel
import com.app.thingscrossing.feature_advertisement.domain.util.AdvertisementSortVariant
import okhttp3.MultipartBody

interface AdvertisementRepository {
    suspend fun getAdvertisementList(): List<Advertisement>

    suspend fun getAdvertisementById(id: Int): Advertisement

    suspend fun deleteAdvertisement(advertisement: Advertisement)

    suspend fun insertAdvertisement(advertisement: Advertisement, authKey: String)

    suspend fun searchAdvertisements(
        searchValue: String,
        sortBy: AdvertisementSortVariant,
        isAscending: Boolean
    ): List<Advertisement>

    suspend fun deleteImage(image: ImageModel)

    suspend fun uploadImage(file: MultipartBody.Part): ImageModel

    suspend fun getMyAdvertisementList(authKey: String): List<Advertisement>

    suspend fun updateAdvertisement(advertisement: Advertisement, authKey: String)
}