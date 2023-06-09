package com.app.thingscrossing.feature_advertisement.presentation.util

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChangeCircle
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Redeem
import androidx.compose.ui.graphics.vector.ImageVector
import com.app.thingscrossing.R

sealed interface FilterOption {
    @get:StringRes
    val nameId: Int
    val icon: ImageVector

    object Exchange: FilterOption {
        override val nameId: Int
            get() = R.string.exchange
        override val icon: ImageVector
            get() = Icons.Default.ChangeCircle
    }

    object Trade: FilterOption {
        override val nameId: Int
            get() = R.string.trade
        override val icon: ImageVector
            get() = Icons.Default.Payment
    }

    object Gift: FilterOption {
        override val nameId: Int
            get() = R.string.gift
        override val icon: ImageVector
            get() = Icons.Default.Redeem
    }

    companion object {
        val ALL = listOf(
            Exchange,
            Trade,
            Gift
        )
    }
}