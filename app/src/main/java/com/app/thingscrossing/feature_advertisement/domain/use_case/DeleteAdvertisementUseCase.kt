package com.app.thingscrossing.feature_advertisement.domain.use_case

import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository

class DeleteAdvertisementUseCase(
    private val repository: AdvertisementRepository,
) {
    suspend operator fun invoke(advertisement: Advertisement) {
        repository.deleteAdvertisement(advertisement)
    }
}