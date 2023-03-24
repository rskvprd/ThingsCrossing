package com.app.thingscrossing.feature_advertisement.data.source.local

import androidx.room.*
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import kotlinx.coroutines.flow.Flow

@Dao
interface AdvertisementDao {
    @Query("SELECT * FROM advertisement")
    fun getAdvertisementList(): Flow<List<Advertisement>>

    @Query("SELECT * FROM advertisement WHERE id = :id")
    suspend fun getAdvertisementById(id: String): Advertisement?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdvertisement(advertisement: Advertisement)

    @Delete
    suspend fun deleteAdvertisement(advertisement: Advertisement)
}