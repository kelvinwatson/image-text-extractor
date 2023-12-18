package com.determinasian.eventsdatagenerator.ui.composables

import android.net.Uri
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.determinasian.eventsdatagenerator.R
import com.determinasian.eventsdatagenerator.extension.findComponentActivity
import com.determinasian.eventsdatagenerator.photopicker.PickMediaLauncher
import com.determinasian.eventsdatagenerator.ui.state.UiState

@Composable
fun TfModelCreation(
    paddingValues: PaddingValues,
    uiState: UiState,
    onUris: ActivityResultCallback<List<Uri>>
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        item {
            val componentActivity = LocalContext.current.findComponentActivity() ?: return@item


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.card_margin)
                )
            ) {

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = dimensionResource(id = R.dimen.min_touch_target))
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(id = R.dimen.card_content_padding))
                    ) {
                        Text(
                            text = stringResource(id = R.string.select_photos),
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Text(
                            text = stringResource(id = R.string.select_photos_explanation),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Button(
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(
                                    top = dimensionResource(
                                        id = R.dimen.grid_margin_1
                                    )
                                ),
                            onClick = {
                                PickMediaLauncher.getInstance(componentActivity, onUris).launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            }) {
                            Text(
                                text = stringResource(id = R.string.select_photos),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = dimensionResource(id = R.dimen.min_touch_target))
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(id = R.dimen.card_content_padding))
                    ) {
                        Text(
                            text = stringResource(id = R.string.take_photo),
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Text(
                            text = stringResource(id = R.string.take_photo_explanation),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Button(
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(
                                    top = dimensionResource(
                                        id = R.dimen.grid_margin_1
                                    )
                                ),
                            onClick = {
                                PickMediaLauncher.getInstance(componentActivity, onUris).launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            }) {
                            Text(
                                text = stringResource(id = R.string.select_photos),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }

        item {
            // resulting text

            Column(modifier = Modifier.fillMaxWidth()) {

                when (uiState) {
                    is UiState.NotStarted -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                                .padding(dimensionResource(id = R.dimen.grid_margin_4)),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.twotone_tag_faces_24),
                                contentDescription = null
                            )
                            Text(text = stringResource(id = R.string.not_started_explanation))
                        }
                    }

                    is UiState.Processing -> {
                        Text(text = stringResource(id = R.string.processing))
                    }

                    is UiState.Error -> {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(text = stringResource(id = R.string.error_processing_image))
                            uiState.errorMessage?.let { errMessage ->
                                Text(text = errMessage)
                            }
                        }
                    }

                    is UiState.Complete -> {
                        Text(text = stringResource(id = R.string.text_extraction_complete))
                        if (uiState.text != null) {
                            Text(text = uiState.text.text)
                            Image(
                                painter = rememberAsyncImagePainter(
                                    model = uiState.imageUri,
                                    //If you set a custom ContentScale on the Image that's rendering the AsyncImagePainter, you should also set it in rememberAsyncImagePainter. It's necessary to determine the correct dimensions to load the image at.
                                    //https://coil-kt.github.io/coil/compose/
                                    contentScale = ContentScale.Crop
                                ),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 200.dp),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Text(text = stringResource(R.string.no_text_extracted))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    TfModelCreation(
        paddingValues = PaddingValues(top = 48.dp),
        uiState = UiState.Processing,
        onUris = {}
    )
}
