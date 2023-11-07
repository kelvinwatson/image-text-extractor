package com.determinasian.imagetextextractor

import android.net.Uri
import com.google.mlkit.vision.text.Text

sealed interface UiState {

    object NotStarted : UiState

    object Processing : UiState

    data class Complete(
        val text: Text? = null,
        val imageUri: Uri? = null
    ) : UiState

    data class Error(
        val exception: Exception,
        val errorMessage: String? = null
    ) : UiState
}

