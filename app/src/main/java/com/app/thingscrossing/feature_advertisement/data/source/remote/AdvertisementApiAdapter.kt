package com.app.thingscrossing.feature_advertisement.data.source.remote

import com.app.thingscrossing.feature_advertisement.domain.model.Currency
import com.google.gson.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.time.LocalDateTime

const val ADVERTISEMENT_API_BASE_URL = "http://192.168.50.41:8000"

class ApiAdapter {
    companion object {
        fun buildAdvertisementApi(): AdvertisementApi {
            return Retrofit.Builder()
                .baseUrl(ADVERTISEMENT_API_BASE_URL)
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder()
                            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                            .registerTypeAdapter(
                                LocalDateTime::class.java,
                                LocalDateTimeDeserializer()
                            )
                            .registerTypeAdapter(
                                Currency::class.java,
                                CurrencyDeserializer()
                            ).create()
                    )
                )
                .build()
                .create(AdvertisementApi::class.java)
        }

    }

}

class LocalDateTimeDeserializer : JsonDeserializer<LocalDateTime> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): LocalDateTime {
        return LocalDateTime.parse(json?.asString?.dropLast(1))
    }
}

class CurrencyDeserializer : JsonDeserializer<Currency> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Currency {
        return Currency.fromCode(json?.asString!!)
    }
}