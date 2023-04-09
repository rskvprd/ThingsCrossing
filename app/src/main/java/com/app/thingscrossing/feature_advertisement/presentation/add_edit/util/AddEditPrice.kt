package com.app.thingscrossing.feature_advertisement.presentation.add_edit.util

import com.app.thingscrossing.feature_advertisement.domain.model.Currency
import com.app.thingscrossing.feature_advertisement.domain.model.Price
import kotlin.math.pow

data class AddEditPrice(
    val value: String = "",
    val currency: Currency,
) {
    fun toPrice(): Price {
        return Price(
            value = value.toDouble(),
            currency = currency
        )
    }

    companion object {
        fun isValid(price: AddEditPrice): Boolean {
            if (price.value.isBlank()) return true
            return try {
                val parts = price.value.split(".")
                if (parts.size > 1 && parts[1].length > 2) {
                    return false
                }
                val doublePrice = price.value.toDouble()
                if (doublePrice >= 10.0.pow(30))
                    return false
                true
            } catch (e: NumberFormatException) {
                false
            }
        }
    }
}

