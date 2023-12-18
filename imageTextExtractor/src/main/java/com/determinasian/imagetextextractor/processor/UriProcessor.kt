package com.determinasian.imagetextextractor.processor

import android.content.Context
import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.text.Text

interface UriProcessor {
    operator fun invoke(
        context: Context,
        uris: List<Uri?>,
        onProcessingImage: () -> Unit
    ): Task<Text?>?
}