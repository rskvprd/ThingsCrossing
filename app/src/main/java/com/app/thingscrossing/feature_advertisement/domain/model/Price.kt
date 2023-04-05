package com.app.thingscrossing.feature_advertisement.domain.model

import com.google.gson.annotations.SerializedName

data class Price(
    val id: Int? = null,
    val value: Double,
    @SerializedName("currency_code") val currency: Currency,
) {
    companion object {
        val DEFAULT = Price(
            value = 0.0,
            currency = Currency.RUB)
    }
}