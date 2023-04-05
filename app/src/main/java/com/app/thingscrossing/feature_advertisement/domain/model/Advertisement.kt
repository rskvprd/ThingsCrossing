package com.app.thingscrossing.feature_advertisement.domain.model

import java.time.LocalDateTime


data class Advertisement(
    val id: Int? = null,
    val title: String,
    val description: String,
    val prices: List<Price>,
    val address: String,
    val images: List<ImageModel>,
    val characteristics: List<Characteristic>,
    val exchanges: List<Exchange>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val categories: List<Category>,
) {
    companion object {
        val DEFAULT: Advertisement = Advertisement(
            title = "",
            description = "",
            prices = ArrayList(),
            address = "",
            characteristics = ArrayList(),
            images = ArrayList(),
            exchanges = ArrayList(),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            categories = ArrayList(),
            )
    }
}