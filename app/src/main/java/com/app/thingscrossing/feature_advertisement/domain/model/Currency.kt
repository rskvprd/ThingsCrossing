package com.app.thingscrossing.feature_advertisement.domain.model

import androidx.annotation.StringRes
import com.app.thingscrossing.R

sealed class Currency (@StringRes val name: Int, val symbol: String, val code: String) {
    object USD : Currency(R.string.usd, "$", "USD")
    object RUB : Currency(R.string.rub, "₽", "RUB")
    object KZT : Currency(R.string.kzt, "₸", "KZT")
    object UAH : Currency(R.string.uah, "₴", "UAH")

    companion object {
        fun fromCode(code: String) : Currency {
            return when (code) {
                USD.code -> USD
                RUB.code -> RUB
                KZT.code -> KZT
                UAH.code -> UAH
                else -> throw NoSuchElementException("There is no currency with code $code")
            }
        }

        fun getAvailableCurrencies() : List<Currency> {
            return listOf(
                USD,
                RUB,
                KZT,
                UAH
            )
        }
    }
}