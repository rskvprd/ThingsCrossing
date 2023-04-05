package com.app.thingscrossing.feature_advertisement.presentation.add_edit.components

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import com.app.thingscrossing.R
import com.app.thingscrossing.feature_advertisement.presentation.add_edit.AddEditState
import com.app.thingscrossing.ui.theme.ThingsCrossingTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddEditImagesBlock(
    onPickImage: (uri: Uri?) -> Unit,
    onConfirmImage: () -> Unit,
    onDismissImage: () -> Unit,
    uiState: AddEditState,
    images: List<@Composable () -> Unit>,
) {
    HorizontalPager(pageCount = uiState.images.size) {
        images[it]()
    }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { onPickImage(it) }
        }

    AddButton(
        onClick = { launcher.launch("image/*") },
        textId = R.string.add_image
    )

    uiState.bitmap?.let { bitmap ->
        AddImageDialog(
            onDismissImage,
            onConfirmImage,
            bitmap.asImageBitmap(),
        )
    }
}


@Composable
fun AddImageDialog(
    onDismissImage: () -> Unit,
    onConfirmImage: () -> Unit,
    bitmap: ImageBitmap,
) {
    AlertDialog(
        onDismissRequest = {
            onDismissImage()
        },
        dismissButton = {
            TextButton(onClick = { onDismissImage() }) {
                Text(text = stringResource(id = R.string.drop_image))
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirmImage() }) {
                Text(text = stringResource(id = R.string.upload_image))
            }
        },
        text = {
            Card(
                shape = RoundedCornerShape(20.dp),
            ) {
                Image(
                    bitmap = bitmap,
                    contentDescription = null,
                )
            }
        }
    )
}


@Preview
@Composable
fun AddEditImage_Preview() {
    ThingsCrossingTheme {
        AddEditImagesBlock(
            onPickImage = { /*TODO*/ },
            uiState = AddEditState(
                bitmap = createBitmap(400, 400, Bitmap.Config.RGB_565)
            ),
            onDismissImage = { /*TODO*/ },
            onConfirmImage = { /*TODO*/ },
            images = listOf(
                {
                    Image(
                        painter = painterResource(id = R.mipmap.ic_launcher),
                        contentDescription = null
                    )
                },
                {
                    Image(
                        painter = painterResource(id = R.mipmap.ic_launcher),
                        contentDescription = null
                    )
                },
                {
                    Image(
                        painter = painterResource(id = R.mipmap.ic_launcher),
                        contentDescription = null
                    )
                },
            )
        )
    }
}
