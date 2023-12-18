package com.determinasian.imagetextextractor.processor

import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import javax.inject.Provider

@ViewModelScoped
class ImageProcessorImpl @Inject constructor(
) : ImageProcessor {

    private val textRecognizer: Provider<TextRecognizer> = Provider {
        TextRecognition.getClient(
            TextRecognizerOptions.DEFAULT_OPTIONS
        )
    }

    //TODO Return a wrapper that includes a status, such as an error
    override fun invoke(onProcessingImage: () -> Unit, input: InputImage?): Task<Text?>? {
        onProcessingImage()
        return input?.let {
            textRecognizer.get().process(it)
        }
    }
}