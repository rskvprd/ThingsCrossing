package com.app.thingscrossing.feature_advertisement.domain.use_case

import com.app.thingscrossing.R
import com.app.thingscrossing.core.Resource
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.model.Category
import com.app.thingscrossing.feature_advertisement.domain.model.Characteristic
import com.app.thingscrossing.feature_advertisement.domain.model.Exchange
import com.app.thingscrossing.feature_advertisement.domain.model.ImageModel
import com.app.thingscrossing.feature_advertisement.domain.repository.AdvertisementRepository
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.util.AddEditPrice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import java.util.InvalidPropertiesFormatException

class AddAdvertisementUseCase(
    private val repository: AdvertisementRepository,
) {
    operator fun invoke(advertisement: Advertisement, authKey: String): Flow<Resource<Unit>> =
        flow {
            if (advertisement.title.isBlank()) {
                emit(Resource.Error(R.string.blank_title_error))
                return@flow
            }
            if (advertisement.description.isBlank()) {
                emit(Resource.Error(R.string.blank_description_error))
                return@flow
            }
            if (advertisement.address.isBlank()) {
                emit(Resource.Error(R.string.blank_address_error))
                return@flow
            }
            if (advertisement.createdAt != null) {
                throw InvalidPropertiesFormatException(
                    "createdAt field should be null when adding new advertisement"
                )
            }
            if (advertisement.updatedAt != null) {
                throw InvalidPropertiesFormatException(
                    "updatedAt field should be null when adding new advertisement"
                )
            }
            if (advertisement.id != null) {
                throw InvalidPropertiesFormatException(
                    "id field should be null when adding new advertisement"
                )
            }

            emitAll(Resource.defaultHandleApiResource {
                repository.insertAdvertisement(
                    advertisement = advertisement,
                    authKey = authKey
                )
            })
        }

    operator fun invoke(
        title: String,
        description: String,
        prices: List<AddEditPrice>,
        address: String,
        images: List<ImageModel>,
        characteristics: List<Characteristic>,
        exchanges: List<Exchange>,
        categories: List<Category>,
        authKey: String,
    ) = flow {
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
        )
        emitAll(Resource.defaultHandleApiResource {
            repository.insertAdvertisement(
                advertisement = advertisement,
                authKey = authKey
            )
        })

    }
}