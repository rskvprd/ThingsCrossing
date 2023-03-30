package com.app.thingscrossing.feature_advertisement.data.source.local

import androidx.room.*
import com.app.thingscrossing.feature_advertisement.data.source.local.entity.AdvertisementEntity

@Dao
interface AdvertisementDao {
    @Query("SELECT * FROM advertisement")
    suspend fun getAdvertisementList(): List<AdvertisementEntity>

    @Query("SELECT * FROM advertisement WHERE id = :id")
    suspend fun getAdvertisementById(id: String): AdvertisementEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdvertisement(advertisement: AdvertisementEntity)

    @Delete
    suspend fun deleteAdvertisement(advertisement: AdvertisementEntity)
}