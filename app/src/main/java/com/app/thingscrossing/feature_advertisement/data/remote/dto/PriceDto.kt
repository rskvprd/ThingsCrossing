package com.app.thingscrossing.feature_advertisement.data.remote.dto

import com.app.thingscrossing.feature_advertisement.domain.model.Currency
import com.app.thingscrossing.feature_advertisement.domain.model.Price

data class PriceDto(
    val currencyCode: String,
    val value: Double
) {
    companion object {
        fun fromPrice(price: Price): PriceDto {
            return PriceDto(
                currencyCode = price.currency.code,
                value = price.value,
            )
        }
    }
}

fun PriceDto.toPrice(): Price {
    return Price(
        value = value,
        currency = Currency.fromCode(currencyCode)
    )
}
