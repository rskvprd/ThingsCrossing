package com.app.thingscrossing.feature_advertisement.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "advertisement")
data class Advertisement(
    val title: String,
    val description: String,
    val price: Int,
    val address: String,
    val imageUrls: ArrayList<String>,
    val characteristics: Map<String, String>,
    val exchange: ArrayList<String>,
    val createdAt: String, // TODO: the type of createdAt field should be LocalDateTime
    @PrimaryKey val id: Int? = null,
)

class InvalidAdvertisementException(message: String) : java.lang.Exception(message)