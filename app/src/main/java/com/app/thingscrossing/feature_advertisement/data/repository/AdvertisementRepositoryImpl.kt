package com.app.thingscrossing.feature_advertisement.data.repository

import com.app.thingscrossing.core.Constants.AUTH_TOKEN_PREFIX
import com.app.thingscrossing.feature_account.data.local.toAuthKey
import com.app.thingscrossing.feature_advertisement.data.remote.AdvertisementApi
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.model.ImageModel
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import com.app.thingscrossing.feature_advertisement.domain.util.AdvertisementSortVariant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject


class AdvertisementRepositoryImpl @Inject constructor(
    private val api: AdvertisementApi
) : AdvertisementRepository {
    override suspend fun deleteImage(image: ImageModel) =
        withContext(Dispatchers.IO) {
            api.deletePicture(image.id!!)
        }

    override suspend fun uploadImage(file: MultipartBody.Part): ImageModel =
        withContext(Dispatchers.IO) {
            api.uploadImage(
                image = file,
            )
        }

    override suspend fun getMyAdvertisementList(authKey: String): List<Advertisement> =
        withContext(Dispatchers.IO) {
            api.getMyAdvertisement(authKey)
        }

    override suspend fun updateAdvertisement(advertisement: Advertisement, authKey: String) {
        withContext(Dispatchers.IO) {
            api.updateAdvertisement(authKey, advertisement.id!!, advertisement)
        }
    }


    override suspend fun getAdvertisementList(): List<Advertisement> =
        withContext(Dispatchers.IO) {
            api.getAdvertisementList()
        }

    override suspend fun getAdvertisementById(id: Int): Advertisement =
        withContext(Dispatchers.IO) {
            api.getAdvertisementById(id = id.toString())
        }


    override suspend fun deleteAdvertisement(advertisement: Advertisement) {
        withContext(Dispatchers.IO) {
            api.deleteAdvertisement(advertisement.id!!)
        }
    }


    override suspend fun insertAdvertisement(advertisement: Advertisement, authKey: String) {
        withContext(Dispatchers.IO) {
            api.insertAdvertisement(advertisement, authKey = "$AUTH_TOKEN_PREFIX $authKey")
        }
    }

    override suspend fun searchAdvertisements(
        searchValue: String,
        sortBy: AdvertisementSortVariant,
        isAscending: Boolean
    ): List<Advertisement> =
        withContext(Dispatchers.IO) {
            api.searchAdvertisements(
                searchValue = searchValue,
                sortBy = sortBy.name,
                isAscending = isAscending
            )
        }
}
