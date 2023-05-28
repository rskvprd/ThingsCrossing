package com.app.thingscrossing.core

import com.app.thingscrossing.core.Constants.THINGS_CROSSING_API_BASE_URL
import com.app.thingscrossing.feature_account.data.remote.AccountApi
import com.app.thingscrossing.feature_advertisement.data.remote.AdvertisementApi
import com.app.thingscrossing.feature_advertisement.domain.model.Currency
import com.app.thingscrossing.feature_chat.data.remote.ChatApi
import com.google.gson.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.time.LocalDateTime


class ApiAdapter {
    companion object {
        private val thingsCrossingRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(THINGS_CROSSING_API_BASE_URL)
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
                        )
                        .registerTypeAdapter(
                            Currency::class.java,
                            CurrencySerializer()
                        )
                        .create()
                )
            )
            .build()

        fun buildAdvertisementApi(): AdvertisementApi =
            thingsCrossingRetrofit.create(AdvertisementApi::class.java)

        fun buildAccountApi(): AccountApi =
            thingsCrossingRetrofit.create(AccountApi::class.java)

        fun buildChatApi(): ChatApi =
            thingsCrossingRetrofit.create(ChatApi::class.java)
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

class CurrencySerializer : JsonSerializer<Currency> {
    override fun serialize(
        src: Currency?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?,
    ): JsonElement {
        return JsonPrimitive(src?.code)
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

fun String.globalUrl(): String {
    return "$THINGS_CROSSING_API_BASE_URL$this"
}