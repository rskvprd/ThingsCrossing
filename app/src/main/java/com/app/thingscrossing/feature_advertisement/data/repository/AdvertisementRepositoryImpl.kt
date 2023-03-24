package com.app.thingscrossing.feature_advertisement.data.repository

import com.app.thingscrossing.feature_advertisement.data.source.local.AdvertisementDao
import com.app.thingscrossing.feature_advertisement.data.source.remote.AdvertisementApi
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject


class AdvertisementRepositoryImpl @Inject constructor(
    private val api: AdvertisementApi,
    private val dao: AdvertisementDao,
) : AdvertisementRepository {
    override suspend fun getAdvertisementList(): List<Advertisement> =
        withContext(Dispatchers.IO) {
            val call = api.getAdvertisementList()
            return@withContext try {
                call.execute().body() ?: emptyList()
            } catch (ex: HttpException) {
                emptyList()
            }
        }

    override suspend fun getAdvertisementById(id: Int): Advertisement? =
        withContext(Dispatchers.IO) {
            val call = api.getAdvertisementById(id = id.toString())
            return@withContext try {
                call.execute().body()
            } catch (ex: HttpException) {
                null
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

    override suspend fun searchAdvertisements(searchValue: String): List<Advertisement> =
        withContext(Dispatchers.IO) {
            val call = api.searchAdvertisements(searchValue)
            try {
                call.execute().body() ?: emptyList()
            } catch (ex: HttpException) {
                emptyList()
            }
        }
}
