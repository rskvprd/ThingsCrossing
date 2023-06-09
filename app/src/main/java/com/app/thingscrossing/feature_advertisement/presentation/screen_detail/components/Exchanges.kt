package com.app.thingscrossing.feature_advertisement.presentation.screen_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_advertisement.domain.model.Exchange
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.components.ExchangeList

@Composable
fun Exchanges(exchanges: List<Exchange>) {
    Column {
        if (exchanges.isNotEmpty()){
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Text(text = "${stringResource(id = R.string.change_on)}: ")
                ExchangeList(
                    exchangeList = exchanges,
                    onDeleteExchange = {},
                    readOnly = true
                )
            }
        }
    }
}