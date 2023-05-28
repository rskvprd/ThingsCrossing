package com.app.thingscrossing.feature_chat.presentation.private_chat.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.thingscrossing.R

@Composable
fun ChatBottomBar(
    message: String,
    onMessageChange: (String) -> Unit,
    onMessageSend: () -> Unit,
) {
    BottomAppBar() {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        ) {
            OutlinedTextField(
                modifier = Modifier.width(300.dp),
                value = message,
                onValueChange = onMessageChange,
                placeholder = { Text(text = stringResource(id = R.string.message_placeholder)) }
            )
            IconButton(onClick = onMessageSend) {
                Icon(imageVector = Icons.Default.Send, contentDescription = null)
            }
        }
    }
}