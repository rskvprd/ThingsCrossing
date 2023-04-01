package com.app.thingscrossing.feature_advertisement.domain.repository

import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement

interface AdvertisementRepository {

    suspend fun getAdvertisementList(): List<Advertisement>

    suspend fun getAdvertisementById(id: Int): Advertisement

    suspend fun deleteAdvertisement(advertisement: Advertisement)

    suspend fun insertAdvertisement(advertisement: Advertisement)

    suspend fun searchAdvertisements(searchValue: String): List<Advertisement>
}