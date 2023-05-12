package com.app.thingscrossing.feature_advertisement.domain.use_case

import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_advertisement.domain.model.ImageModel
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import kotlinx.coroutines.flow.Flow

class DeleteImageUseCase(
    private val repository: AdvertisementRepository,
) {
    operator fun invoke(
        image: ImageModel,
    ): Flow<Resource<Unit>> {
        return Resource.defaultHandleApiResource { repository.deleteImage(image) }
    }
}