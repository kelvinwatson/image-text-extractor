package com.determinasian.imagetextextractor.processor

import android.content.Context
import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import dagger.hilt.android.scopes.ViewModelScoped
import java.io.IOException
import javax.inject.Inject
import javax.inject.Provider

@ViewModelScoped
class UriProcessorImpl @Inject constructor(
    private val imageProcessor: Provider<ImageProcessor>,
) : UriProcessor {

    //TODO Return a wrapper that includes a status, such as an error
    override fun invoke(
        context: Context,
        uris: List<Uri?>,
        onProcessingImage: () -> Unit
    ): Task<Text?>? = uris[0]?.let { uri ->

        //Process the first image
        //TODO process multiple images in parallel using coroutines

        try {
            val image: InputImage = InputImage.fromFilePath(context, uri)
            imageProcessor.get()(onProcessingImage, image)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}