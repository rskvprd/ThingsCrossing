package com.app.thingscrossing.feature_advertisement.domain.model

data class Price(
    val value: Float,
    val currencyCode: Currency,
) {
    companion object {
        val DEFAULT = Price(0f, Currency.RUB)
    }
}