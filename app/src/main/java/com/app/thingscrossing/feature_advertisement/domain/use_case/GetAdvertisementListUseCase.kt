package com.app.thingscrossing.feature_advertisement.domain.use_case

import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import com.app.thingscrossing.feature_advertisement.domain.util.AdvertisementOrder
import com.app.thingscrossing.feature_advertisement.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import java.net.ConnectException

class GetAdvertisementListUseCase(
    private val repository: AdvertisementRepository,
) {
    @kotlin.jvm.Throws(ConnectException::class)
    operator fun invoke(
        advertisementOrder: AdvertisementOrder = AdvertisementOrder.Date(OrderType.Descending),
    ): Flow<Resource<List<Advertisement>>> {
        return Resource.getResource { repository.getAdvertisementList() }
    }
}