package com.app.thingscrossing.feature_advertisement.domain.use_case

import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import com.app.thingscrossing.feature_advertisement.domain.util.AdvertisementOrder
import com.app.thingscrossing.feature_advertisement.domain.util.OrderType

class GetAdvertisementListUseCase(
    private val repository: AdvertisementRepository,
) {
    suspend operator fun invoke(
        advertisementOrder: AdvertisementOrder = AdvertisementOrder.Date(OrderType.Descending),
    ): List<Advertisement> {
        val advertisements = repository.getAdvertisementList()
        return when (advertisementOrder.orderType) {
            is OrderType.Ascending -> {
                when (advertisementOrder) {
                    is AdvertisementOrder.Date -> advertisements.sortedBy { it.createdAt }
                    is AdvertisementOrder.Title -> advertisements.sortedBy { it.title }
                }
            }
            is OrderType.Descending -> {
                when (advertisementOrder) {
                    is AdvertisementOrder.Date -> advertisements.sortedByDescending { it.createdAt }
                    is AdvertisementOrder.Title -> advertisements.sortedByDescending { it.title }
                }
            }
        }
    }
}