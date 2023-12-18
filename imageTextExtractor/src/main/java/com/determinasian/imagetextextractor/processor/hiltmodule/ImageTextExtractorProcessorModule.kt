package com.determinasian.imagetextextractor.processor.hiltmodule

import com.determinasian.imagetextextractor.processor.ImageProcessor
import com.determinasian.imagetextextractor.processor.ImageProcessorImpl
import com.determinasian.imagetextextractor.processor.UriProcessor
import com.determinasian.imagetextextractor.processor.UriProcessorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ImageTextExtractorProcessorModule {
    @Binds
    abstract fun bindUriProcessorImpl(impl: UriProcessorImpl): UriProcessor

    @Binds
    abstract fun bindImageProcessorImpl(impl: ImageProcessorImpl): ImageProcessor
}