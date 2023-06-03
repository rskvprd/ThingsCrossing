package com.app.thingscrossing.feature_advertisement.domain.use_case

import com.app.thingscrossing.R
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_account.data.local.toAuthKey
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.model.Category
import com.app.thingscrossing.feature_advertisement.domain.model.Characteristic
import com.app.thingscrossing.feature_advertisement.domain.model.Exchange
import com.app.thingscrossing.feature_advertisement.domain.model.ImageModel
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.util.AddEditPrice
import com.app.thingscrossing.services.AuthService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import java.util.InvalidPropertiesFormatException

class UpdateAdvertisement(
    private val repository: AdvertisementRepository,
    private val authService: AuthService,
) {
    operator fun invoke(
        id: Int,
        title: String,
        description: String,
        prices: List<AddEditPrice>,
        address: String,
        images: List<ImageModel>,
        characteristics: List<Characteristic>,
        exchanges: List<Exchange>,
        categories: List<Category>,
    ) = flow {
        emit(Resource.Loading())
        while(!authService.isInitialized) {
            delay(10)
        }
        val authKey = authService.authKey!!.toAuthKey()

        if (title.isBlank()) {
            emit(Resource.Error(R.string.blank_title_error))
            return@flow
        }
        if (description.isBlank()) {
            emit(Resource.Error(R.string.blank_description_error))
            return@flow
        }
        if (address.isBlank()) {
            emit(Resource.Error(R.string.blank_address_error))
            return@flow
        }
        if (prices.any { price -> price.value.toDoubleOrNull() == null }) {
            emit(Resource.Error(R.string.blank_address_error))
            return@flow
        }
        val advertisement = Advertisement(
            title = title,
            description = description,
            prices = prices.map { it.toPrice() },
            address = address,
            images = images,
            characteristics = characteristics,
            exchanges = exchanges,
            categories = categories,
            id = id,
        )
        emitAll(Resource.defaultHandleApiResource {
            repository.updateAdvertisement(
                advertisement = advertisement,
                authKey = authKey
            )
        })

    }
}