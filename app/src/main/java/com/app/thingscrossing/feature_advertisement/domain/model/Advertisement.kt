package com.app.thingscrossing.feature_advertisement.domain.model

import com.app.thingscrossing.feature_advertisement.data.remote.dto.Characteristic
import java.time.LocalDateTime


data class Advertisement(
    val id: Int = 0,
    val title: String,
    val description: String,
    val prices: List<Price>,
    val address: String,
    val imageUrls: List<String>,
    val characteristics: List<Characteristic>,
    val exchanges: List<String>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val categories: List<String>,
) {
    companion object {
        val DEFAULT: Advertisement = Advertisement(
            title = "",
            description = "",
            prices = ArrayList(),
            address = "",
            characteristics = ArrayList(),
            imageUrls = ArrayList(),
            exchanges = ArrayList(),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            categories = ArrayList(),
            )
    }
}