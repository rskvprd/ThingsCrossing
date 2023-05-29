package com.app.thingscrossing.feature_account.presentation.profile.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.thingscrossing.R
import com.app.thingscrossing.core.util.ComposeFileProvider
import com.app.thingscrossing.feature_account.presentation.profile.ProfileState
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.components.ImagePreviewDialog
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.components.SelectImageSourceDialog

@Composable
fun ProfileAvatar(
    imageUrl: String,
    onPickImage: (uri: Uri?) -> Unit,
    onConfirmImage: () -> Unit,
    onDropImage: () -> Unit,
    onAddImageClick: () -> Unit,
    dismissAddImageDialog: () -> Unit,
    uiState: ProfileState,
) {
    val context = LocalContext.current

    val pickFromGalleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { onPickImage(it) }
        }

    val uri = ComposeFileProvider.getImageUri(context)
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = {
            if (!it) onDropImage()
            else onPickImage(uri)
        })


    if (uiState.showAddImageDialog) {
        SelectImageSourceDialog(onCameraSelect = {
            cameraLauncher.launch(uri)
        }, onGallerySelect = {
            pickFromGalleryLauncher.launch("image/*")
        }, onDismissRequest = {
            dismissAddImageDialog()
        })
    } else if (uiState.currentImageUri != null) {
        ImagePreviewDialog(
            onDismissImage = onDropImage,
            onConfirmImage = onConfirmImage,
            imageUri = uiState.currentImageUri,
        )
    }


    Box {
        AsyncImage(
            model = imageUrl,
            contentDescription = stringResource(id = R.string.avatar_image_cont_desc),
            modifier = Modifier
                .clip(CircleShape)
                .size(130.dp),
            contentScale = ContentScale.Crop
        )
        IconButton(
            onClick = { onAddImageClick() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface),
        ) {
            Icon(imageVector = Icons.Default.AddAPhoto, contentDescription = null)
        }
    }

}