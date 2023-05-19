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
            title = "",
            description = "",
            prices = ArrayList(),
            address = "",
            characteristics = ArrayList(),
            images = ArrayList(),
            exchanges = ArrayList(),
            categories = ArrayList(),
        )
    }
}