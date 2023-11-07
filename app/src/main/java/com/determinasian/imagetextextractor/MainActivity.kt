package com.determinasian.imagetextextractor

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.determinasian.imagetextextractor.ui.theme.ImageTextExtractorTheme


class MainActivity : ComponentActivity() {

    private val uriProcessingViewModel: UriProcessingViewModel by viewModels { ViewModelProvider.NewInstanceFactory.instance }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // https://developer.android.com/training/data-storage/shared/photopicker
        // Registers a photo picker activity launcher in single-select mode. This must occur before
        // MainActivity.onStart
        uriProcessingViewModel.initPhotoPicker(this)

        setContent {
            ImageTextExtractorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val uiState = uriProcessingViewModel.uiState.collectAsState().value

                    Home(
                        uriProcessingViewModel.onUris,
                        uiState
                    )
                }
            }
        }
    }
}

@Composable
fun Home(onUris: ActivityResultCallback<List<Uri>>, uiState: UiState) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        item {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineMedium
            )
        }

        item {
            val componentActivity = LocalContext.current.findComponentActivity() ?: return@item
            Button(onClick = {
                PickMediaLauncher.getInstance(componentActivity, onUris).launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }) {
                Text(stringResource(id = R.string.select_photos))
            }
        }

        item {
            // resulting text
            val displayStatus = when (uiState) {
                is UiState.NotStarted -> "Select an image"
                is UiState.Processing -> "Processing"
                is UiState.Complete -> "Processing complete"
                is UiState.Error -> "Error processing image: ${uiState.errorMessage}"
            }
            Column {

                Text(text = displayStatus)

                if (uiState is UiState.Complete && uiState.text != null) {
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
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    ImageTextExtractorTheme {
        Home({}, UiState.NotStarted)
    }
}