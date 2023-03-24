package com.app.thingscrossing.feature_advertisement.domain.use_case

import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository

class SearchAdvertisementsUseCase(
    val repository: AdvertisementRepository,
) {
    suspend operator fun invoke(searchValue: String): List<Advertisement> {
        return repository.searchAdvertisements(searchValue)
    }
}