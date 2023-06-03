package com.app.thingscrossing.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun ButtonToggleGroup(
    items: List<ButtonToggleGroupItem>,
) {
    val cornerRadius = 8.dp
    Row {
        items.forEachIndexed { index, item ->
            OutlinedButton(
                modifier = when (index) {
                    0 ->
                        Modifier
                            .offset(0.dp, 0.dp)
                            .zIndex(if (item.selected) 1f else 0f)

                    else ->
                        Modifier
                            .offset((-1 * index).dp, 0.dp)
                            .zIndex(if (item.selected) 1f else 0f)
                },
                onClick = { item.onClick() },
                shape = when (index) {
                    // left outer button
                    0 -> RoundedCornerShape(
                        topStart = cornerRadius,
                        topEnd = 0.dp,
                        bottomStart = cornerRadius,
                        bottomEnd = 0.dp
                    )
                    // right outer button
                    items.size - 1 -> RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = cornerRadius,
                        bottomStart = 0.dp,
                        bottomEnd = cornerRadius
                    )
                    // middle button
                    else -> RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                },
                border = BorderStroke(
                    1.dp, if (item.selected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.DarkGray.copy(alpha = 0.75f)
                    }
                ),
                colors = if (item.selected) {
                    // selected colors
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(
                            alpha = 0.1f)
                        , contentColor = MaterialTheme.colorScheme.primary
                    )
                } else {
                    // not selected colors
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                },
            ) {
                Text(
                    text = item.innerText,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp)
                )
            }
        }
    }
}

class ButtonToggleGroupItem(
    val innerText: String,
    val onClick: () -> Unit,
    val selected: Boolean,
)