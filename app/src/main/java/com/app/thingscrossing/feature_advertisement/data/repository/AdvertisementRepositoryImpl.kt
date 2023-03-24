package com.app.thingscrossing.feature_advertisement.data.repository

import com.app.thingscrossing.feature_advertisement.data.source.local.AdvertisementDao
import com.app.thingscrossing.feature_advertisement.data.source.remote.AdvertisementApi
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject


class AdvertisementRepositoryImpl @Inject constructor(
    private val api: AdvertisementApi,
    private val dao: AdvertisementDao,
) : AdvertisementRepository {

    override suspend fun getAdvertisementList(): List<Advertisement> {
        return withContext(Dispatchers.IO) {
            api.getAdvertisementList().execute().body() ?: emptyList()
        }
    }

    override suspend fun getAdvertisementById(id: Int): Advertisement? =
        withContext(Dispatchers.IO) {
            val response: Response<Advertisement> = api.getAdvertisementById(id = id.toString()).execute()
            if (response.message() == "OK") {
                return@withContext response.body()
            } else {
                return@withContext null
            }
        }


    override suspend fun deleteAdvertisement(advertisement: Advertisement) {
        withContext(Dispatchers.IO) {
            api.deleteAdvertisement(advertisement.id!!)
        }
    }

    override suspend fun insertAdvertisement(advertisement: Advertisement) {
        withContext(Dispatchers.IO) {
            api.insertAdvertisement(advertisement)
        }
    }
}