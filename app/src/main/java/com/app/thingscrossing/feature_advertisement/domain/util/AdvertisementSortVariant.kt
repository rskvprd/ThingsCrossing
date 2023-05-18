package com.app.thingscrossing.feature_advertisement.domain.util

sealed interface AdvertisementSortVariant {
    val name: String

    object Title : AdvertisementSortVariant {
        override val name = "title"
    }

    object Date : AdvertisementSortVariant {
        override val name = "date"
    }

    object Price : AdvertisementSortVariant {
        override val name = "price"
    }

    companion object {
        val ALL = listOf(
            Title,
            Date,
            Price,
        )
    }
}