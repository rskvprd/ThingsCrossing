package com.app.thingscrossing.feature_advertisement.domain.model

import java.time.LocalDateTime


data class Advertisement(
    val id: Int = 0,
    val title: String,
    val description: String,
    val prices: ArrayList<Price>,
    val address: String,
    val imageUrls: ArrayList<String>,
    val characteristics: ArrayList<Characteristic>,
    val exchange: ArrayList<String>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        val DEFAULT: Advertisement = Advertisement(
            title = "",
            description = "",
            prices = ArrayList(),
            address = "",
            characteristics = ArrayList(),
            imageUrls = ArrayList(),
            exchange = ArrayList(),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            )
    }
}