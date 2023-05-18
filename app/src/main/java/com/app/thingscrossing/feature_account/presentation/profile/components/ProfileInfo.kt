package com.app.thingscrossing.feature_account.presentation.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.thingscrossing.feature_account.domain.model.User

@Composable
fun ProfileInfo(
    user: User
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = user.username, color = MaterialTheme.colorScheme.onSurface.copy(alpha = .7f))

        Spacer(Modifier.height(20.dp))

        Text(
            text =
            "${user.firstName} ${user.lastName}",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(text = user.email!!, color = MaterialTheme.colorScheme.onSurface.copy(alpha = .7f))
    }
}