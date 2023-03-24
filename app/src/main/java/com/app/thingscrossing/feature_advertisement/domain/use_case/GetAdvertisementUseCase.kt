package com.app.thingscrossing.feature_advertisement.domain.use_case

import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import retrofit2.HttpException

class GetAdvertisementUseCase(
    private val repository: AdvertisementRepository,
) {
    suspend operator fun invoke(id: Int): Advertisement? {
        return try {
            repository.getAdvertisementById(id)
        } catch (httpException: HttpException) {
            return null
        }
    }
}