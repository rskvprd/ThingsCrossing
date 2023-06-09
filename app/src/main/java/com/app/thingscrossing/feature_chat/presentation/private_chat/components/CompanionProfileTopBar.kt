package com.app.thingscrossing.feature_chat.presentation.private_chat.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.thingscrossing.R
import com.app.thingscrossing.core.presentation.components.BackButton
import com.app.thingscrossing.feature_account.domain.model.UserProfile
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanionProfileTopBar(
    profile: UserProfile?,
    isLoading: Boolean,
    navController: NavController,
    shimmer: Shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.Window),
) {
    TopAppBar(
        navigationIcon = {
            BackButton(navController = navController)
        },
        title = {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isLoading) {
                    Card(
                        modifier = Modifier
                            .size(40.dp)
                            .shimmer(shimmer), shape = CircleShape
                    ) {

                    }
                } else {
                    AsyncImage(
                        model = profile!!.avatar,
                        contentDescription = stringResource(id = R.string.user_profile_picture_desc),
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(40.dp),
                        contentScale = ContentScale.Crop
                    )
                }


                Spacer(modifier = Modifier.width(20.dp))

                if (isLoading) {
                    Card(
                        shape = RectangleShape,
                        modifier = Modifier
                            .shimmer(shimmer)
                            .height(20.dp)
                            .width(100.dp),
                    ) {

                    }
                } else {
                    Text(text = "${profile!!.user.firstName} ${profile.user.lastName}")
                }
            }
        })
}