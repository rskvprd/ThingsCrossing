package com.app.thingscrossing.feature_advertisement.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.thingscrossing.feature_advertisement.domain.model.Advertisement
import com.app.thingscrossing.feature_advertisement.domain.util.Converters

@Database(
    entities = [Advertisement::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AdvertisementDatabase : RoomDatabase() {
    abstract val advertisementDao: AdvertisementDao

    companion object {
        const val DATABASE_NAME = "advertisements_db"
    }
}