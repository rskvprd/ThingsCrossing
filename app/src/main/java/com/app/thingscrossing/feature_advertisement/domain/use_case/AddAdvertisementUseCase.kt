package com.app.thingscrossing.feature_advertisement.domain.use_case

import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import java.net.ConnectException
import java.util.InvalidPropertiesFormatException

class AddAdvertisementUseCase(
    private val repository: AdvertisementRepository,
) {
    @Throws(InvalidPropertiesFormatException::class, ConnectException::class)
    suspend operator fun invoke(advertisement: Advertisement) {
        if (advertisement.title.isBlank()) {
            throw InvalidPropertiesFormatException("The title of the ad can't be empty")
        }
        if (advertisement.description.isBlank()) {
            throw InvalidPropertiesFormatException("The description of the ad can't be empty")
        }
        repository.insertAdvertisement(advertisement)
    }
}