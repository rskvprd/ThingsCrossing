package com.app.thingscrossing.feature_advertisement.domain.use_case

import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import com.app.thingscrossing.feature_advertisement.domain.util.AdvertisementOrder
import com.app.thingscrossing.feature_advertisement.domain.util.OrderType
import kotlinx.coroutines.flow.Flow

class SearchAdvertisementsUseCase(
    val repository: AdvertisementRepository,
) {
    operator fun invoke(
        searchValue: String,
        advertisementOrder: AdvertisementOrder = AdvertisementOrder.Date(
            OrderType.Descending
        ),
    ): Flow<Resource<List<Advertisement>>> {
        return Resource.handleResource { repository.searchAdvertisements(searchValue) }
    }
}