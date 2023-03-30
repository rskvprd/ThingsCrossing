package com.app.thingscrossing.feature_advertisement.domain.use_case

import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import com.app.thingscrossing.feature_advertisement.domain.util.AdvertisementOrder
import com.app.thingscrossing.feature_advertisement.domain.util.OrderType
import retrofit2.HttpException
import java.net.ConnectException

class SearchAdvertisementsUseCase(
    val repository: AdvertisementRepository,
) {
    @kotlin.jvm.Throws(ConnectException::class)
    suspend operator fun invoke(
        searchValue: String,
        advertisementOrder: AdvertisementOrder = AdvertisementOrder.Date(
            OrderType.Descending
        ),
    ): List<Advertisement> {
        val advertisements = repository.searchAdvertisements(searchValue)
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