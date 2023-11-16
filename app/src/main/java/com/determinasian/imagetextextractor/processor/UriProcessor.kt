package com.determinasian.imagetextextractor.processor

import android.content.Context
import android.net.Uri
import androidx.arch.core.util.Function
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import java.io.IOException

// Use dependency injection

class UriProcessor(
    private val context: Context,
    private val imageProcessor: ImageProcessor
) : Function<List<Uri?>, Task<Text?>> {

    //TODO Return a wrapper that includes a status, such as an error
    override fun apply(uris: List<Uri?>): Task<Text?>? {

        //Process the first image

        //TODO process multiple images in parallel using coroutines
        val uri = uris[0] ?: return null
        return try {
            val image: InputImage = InputImage.fromFilePath(context, uri)
            imageProcessor.apply(image)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}