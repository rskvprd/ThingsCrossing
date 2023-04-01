package com.app.thingscrossing.feature_advertisement.data.remote.dto

import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import java.time.LocalDateTime

data class AdvertisementDto(
    val id: Int,
    val address: String,
    val categories: List<Category>,
    val characteristics: List<Characteristic>,
    val createdAt: LocalDateTime,
    val description: String,
    val exchanges: List<Exchange>,
    val images: List<Image>,
    val prices: List<PriceDto>,
    val title: String,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun fromAdvertisement(advertisement: Advertisement): AdvertisementDto {
            return AdvertisementDto(
                id = advertisement.id,
                title = advertisement.title,
                description = advertisement.description,
                prices = advertisement.prices.map { PriceDto.fromPrice(it) },
                address = advertisement.address,
                images = advertisement.imageUrls.map { Image(it) },
                characteristics = advertisement.characteristics,
                exchanges = advertisement.exchanges.map { Exchange(it) },
                createdAt = advertisement.createdAt,
                updatedAt = advertisement.updatedAt,
                categories = advertisement.categories.map {Category(it)},
            )
        }
    }
}

fun AdvertisementDto.toAdvertisement(): Advertisement {
    return Advertisement(
        id = id,
        title = title,
        description = description,
        prices = prices.map { it.toPrice() },
        address = address,
        imageUrls = images.map { it.url },
        characteristics = characteristics,
        exchanges = exchanges.map { it.productName },
        createdAt = createdAt,
        updatedAt = updatedAt,
        categories = categories.map { it.name },
    )
}
