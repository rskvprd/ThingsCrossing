package com.app.thingscrossing.feature_advertisement.domain.model

import com.app.thingscrossing.feature_account.domain.model.UserProfile
import java.time.LocalDateTime


data class Advertisement(
    val title: String,
    val description: String,
    val prices: List<Price>,
    val address: String,
    val images: List<ImageModel>,
    val characteristics: List<Characteristic>,
    val exchanges: List<Exchange>,
    val categories: List<Category>,
    val userProfile: UserProfile? = null,
    val id: Int? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
) {
    companion object {
        val DEFAULT: Advertisement = Advertisement(
            title = "iPhone X",
            description = "The best iPhone X in the World!",
            prices = listOf(Price(null, 100.0, Currency.USD)),
            address = "USA, Texas, Wall st., 1",
            characteristics = listOf(Characteristic("display", "100 inch")),
            images = emptyList(),
            exchanges = listOf(Exchange("Samsung Galaxy S20"), Exchange("Xiaomi Redmi Note 5")),
            categories = listOf(Category("Phones"), Category("Apple")),
        )
    }
}