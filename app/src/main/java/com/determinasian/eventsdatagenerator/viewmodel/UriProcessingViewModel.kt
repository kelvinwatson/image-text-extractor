package com.determinasian.eventsdatagenerator.viewmodel

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCallback
import androidx.lifecycle.ViewModel
import com.determinasian.eventsdatagenerator.photopicker.PickMediaLauncher
import com.determinasian.eventsdatagenerator.ui.state.UiState
import com.determinasian.imagetextextractor.processor.UriProcessor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class UriProcessingViewModel @Inject constructor(): ViewModel() {
    val uiState: StateFlow<UiState>
        get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow<UiState>(UiState.NotStarted)

    lateinit var onUris: ActivityResultCallback<List<Uri>>

    // https://developer.android.com/training/data-storage/shared/photopicker
    fun initPhotoPicker(componentActivity: ComponentActivity) {
        PickMediaLauncher.getInstance(componentActivity, ActivityResultCallback<List<Uri>> { uris ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.

            // TODO: Dependency injection
            UriProcessor(
                componentActivity.applicationContext,
                // TODO: Dependency injection
                onProcessingImage = {
                    _uiState.value = UiState.Processing
                }
            ).apply(uris)?.let { task ->
                task.addOnSuccessListener { mlKitText ->
                    _uiState.value = UiState.Complete(
                        text = mlKitText,
                        imageUri = uris[0]
                    )
                }.addOnFailureListener { e ->
                    _uiState.value = UiState.Error(
                        exception = e,

                        )

                }
            } ?: run {
                //task is null

            }
        }.also {
            onUris = it
        })
    }
}
