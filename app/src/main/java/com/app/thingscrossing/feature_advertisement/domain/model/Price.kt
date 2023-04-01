package com.app.thingscrossing.feature_advertisement.domain.model

data class Price(
    val value: Double,
    val currency: Currency,
) {
    companion object {
        val DEFAULT = Price(0.0, Currency.RUB)
    }
}