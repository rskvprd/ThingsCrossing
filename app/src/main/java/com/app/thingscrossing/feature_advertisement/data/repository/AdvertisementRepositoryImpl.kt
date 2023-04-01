package com.app.thingscrossing.feature_advertisement.data.repository

import com.app.thingscrossing.feature_advertisement.data.remote.AdvertisementApi
import com.app.thingscrossing.feature_advertisement.data.remote.dto.AdvertisementDto
import com.app.thingscrossing.feature_advertisement.data.remote.dto.toAdvertisement
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class AdvertisementRepositoryImpl @Inject constructor(
    private val api: AdvertisementApi,
) : AdvertisementRepository {
    override suspend fun getAdvertisementList(): List<Advertisement> =
        withContext(Dispatchers.IO) {
            api.getAdvertisementList().map { it.toAdvertisement() }
        }

    override suspend fun getAdvertisementById(id: Int): Advertisement =
        withContext(Dispatchers.IO) {
            api.getAdvertisementById(id = id.toString()).toAdvertisement()
        }


    override suspend fun deleteAdvertisement(advertisement: Advertisement) {
        withContext(Dispatchers.IO) {
            api.deleteAdvertisement(advertisement.id)
        }
    }


    override suspend fun insertAdvertisement(advertisement: Advertisement) {
        withContext(Dispatchers.IO) {
            api.insertAdvertisement(AdvertisementDto.fromAdvertisement(advertisement))
        }
    }

    override suspend fun searchAdvertisements(searchValue: String): List<Advertisement> =
        withContext(Dispatchers.IO) {
            return@withContext api.searchAdvertisements(searchValue).map {
                it.toAdvertisement()
            }
        }
}
