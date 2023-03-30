package com.app.thingscrossing.feature_advertisement.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "advertisement")
data class AdvertisementEntity(
    @PrimaryKey val id: Int = 0,
    val title: String,
    val description: String,
    val address: String,
    val imageUrls: ArrayList<String>,
    val exchange: ArrayList<String>,
//    val createdAt: LocalDateTime,
)