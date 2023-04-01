package com.app.thingscrossing.feature_advertisement.domain.use_case

import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import kotlinx.coroutines.flow.Flow

class DeleteAdvertisementUseCase(
    private val repository: AdvertisementRepository,
) {
    operator fun invoke(advertisement: Advertisement): Flow<Resource<Unit>> {
        return Resource.postResource { repository.deleteAdvertisement(advertisement) }
    }
}