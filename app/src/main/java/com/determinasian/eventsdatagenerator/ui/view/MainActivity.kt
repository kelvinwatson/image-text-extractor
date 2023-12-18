package com.determinasian.eventsdatagenerator.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.determinasian.eventsdatagenerator.ui.composables.Home
import com.determinasian.eventsdatagenerator.ui.theme.ImageTextExtractorTheme
import com.determinasian.eventsdatagenerator.viewmodel.UriProcessingViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val uriProcessingViewModel: UriProcessingViewModel by viewModels()
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