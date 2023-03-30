package com.app.thingscrossing.feature_advertisement.domain.use_case

import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import java.net.ConnectException

class DeleteAdvertisementUseCase(
    private val repository: AdvertisementRepository,
) {
    @kotlin.jvm.Throws(ConnectException::class)
    suspend operator fun invoke(advertisement: Advertisement) {
        repository.deleteAdvertisement(advertisement)
    }
}