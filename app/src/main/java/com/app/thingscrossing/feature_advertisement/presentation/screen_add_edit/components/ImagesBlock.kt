package com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.thingscrossing.R
import com.app.thingscrossing.core.util.ComposeFileProvider
import com.app.thingscrossing.feature_advertisement.presentation.components.PictureList
import com.app.thingscrossing.feature_advertisement.presentation.screen_add_edit.AddEditState
import com.app.thingscrossing.feature_advertisement.presentation.screen_search.components.PictureItem

@Composable
fun AddEditImagesBlock(
    onPickImage: (uri: Uri?) -> Unit,
    onConfirmImage: () -> Unit,
    onDismissImage: () -> Unit,
    onAddImageClick: () -> Unit,
    uiState: AddEditState,
    dismissAddImageDialog: () -> Unit,
    images: List<@Composable () -> Unit>,
) {
    val context = LocalContext.current
    PictureList(
        imageUrls = uiState.images.map { it.url },
        modifier = Modifier.height(400.dp),
    )

    val pickFromGalleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { onPickImage(it) }
        }

    val uri = ComposeFileProvider.getImageUri(context)
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = {
            if (!it) onDismissImage()
            else onPickImage(uri)
        })


    AddButton(
        onClick = { onAddImageClick() },
        textId = R.string.add_image,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    )

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
            onDismissImage,
            onConfirmImage,
            uiState.currentImageUri,
        )
    }
}

@Composable
fun SelectImageSourceDialog(
    onCameraSelect: () -> Unit,
    onGallerySelect: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(modifier = Modifier.wrapContentHeight(), onDismissRequest = {
        onDismissRequest()
    }, dismissButton = {
        TextButton(onClick = { onCameraSelect() }) {
            Text(
                text = stringResource(id = R.string.select_camera),
            )
        }
    }, confirmButton = {
        TextButton(onClick = { onGallerySelect() }) {
            Text(text = stringResource(id = R.string.select_gallery))
        }
    }, title = {
        Text(
            text = stringResource(id = R.string.select_image_source)
        )
    })
}

@Composable
fun ImagePreviewDialog(
    onDismissImage: () -> Unit,
    onConfirmImage: () -> Unit,
    imageUri: Uri,
) {
    AlertDialog(onDismissRequest = {
        onDismissImage()
    }, dismissButton = {
        TextButton(onClick = { onDismissImage() }) {
            Text(text = stringResource(id = R.string.drop_image))
        }
    }, confirmButton = {
        TextButton(onClick = { onConfirmImage() }) {
            Text(text = stringResource(id = R.string.upload_image))
        }
    }, text = {
        PictureItem(
            modifier = Modifier.height(400.dp),
            model = imageUri,
            contentDescription = stringResource(id = R.string.selected_image),
        )
    })
}