package com.determinasian.imagetextextractor.processor

import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text

interface ImageProcessor {
    operator fun invoke(onProcessingImage: () -> Unit, input: InputImage?): Task<Text?>?
}