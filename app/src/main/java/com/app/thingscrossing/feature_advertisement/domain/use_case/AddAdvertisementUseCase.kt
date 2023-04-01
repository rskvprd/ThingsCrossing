package com.app.thingscrossing.feature_advertisement.domain.use_case

import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import kotlinx.coroutines.flow.Flow
import java.net.ConnectException
import java.util.*

class AddAdvertisementUseCase(
    private val repository: AdvertisementRepository,
) {
    @Throws(InvalidPropertiesFormatException::class, ConnectException::class)
    operator fun invoke(advertisement: Advertisement) : Flow<Resource<Unit>> {
        if (advertisement.title.isBlank()) {
            throw InvalidPropertiesFormatException("The title of the ad can't be empty")
        }

        if (advertisement.description.isBlank()) {
            throw InvalidPropertiesFormatException("The description of the ad can't be empty")
        }

        return Resource.postResource { repository.insertAdvertisement(advertisement) }
    }
}