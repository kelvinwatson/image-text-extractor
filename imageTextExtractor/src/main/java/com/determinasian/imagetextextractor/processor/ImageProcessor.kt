package com.determinasian.imagetextextractor.processor

import androidx.arch.core.util.Function
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class ImageProcessor(
    private val textRecognizer: TextRecognizer =
        TextRecognition.getClient(
            TextRecognizerOptions.DEFAULT_OPTIONS
        ),
    private val onProcessing: () -> Unit
) : Function<InputImage, Task<Text?>> {


    //TODO Return a wrapper that includes a status, such as an error
    override fun apply(input: InputImage?): Task<Text?>? {
        onProcessing()
        return input?.let {
            textRecognizer.process(it)
        }
    }
}