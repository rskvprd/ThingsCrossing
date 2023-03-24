package com.app.thingscrossing.feature_advertisement.domain.use_case

import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.model.InvalidAdvertisementException
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository

class AddAdvertisementUseCase(
    private val repository: AdvertisementRepository,
) {
    @Throws(InvalidAdvertisementException::class)
    suspend operator fun invoke(advertisement: Advertisement) {
        if (advertisement.title.isBlank()) {
            throw InvalidAdvertisementException("The title of the ad can't be empty")
        }
        if (advertisement.description.isBlank()) {
            throw InvalidAdvertisementException("The description of the ad can't be empty")
        }
        repository.insertAdvertisement(advertisement)
    }
}