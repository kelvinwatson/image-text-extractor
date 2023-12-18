package com.determinasian.eventsdatagenerator.ui.state

import android.net.Uri
import com.google.mlkit.vision.text.Text

sealed interface UiState {

    object NotStarted : UiState

    object Processing : UiState

    data class Complete(
        val text: Text? = null, //FIXME shouldn't depend on MLKit type
        val imageUri: Uri? = null
    ) : UiState

    data class Error(
        val exception: Exception,
        val errorMessage: String? = null
    ) : UiState
}

