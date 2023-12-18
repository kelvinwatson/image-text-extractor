package com.determinasian.eventsdatagenerator.photopicker

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts

//TODO: Change to dependency injection to inject singleton

class PickMediaLauncher {

    companion object {
        @Volatile
        private var INSTANCE: ActivityResultLauncher<PickVisualMediaRequest>? = null

        /**
         * Calls register, which must be done before [ComponentActivity.onResume].
         */
        fun getInstance(
            componentActivity: ComponentActivity,
            onUris: ActivityResultCallback<List<Uri>>
        ): ActivityResultLauncher<PickVisualMediaRequest> =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: createInstance(componentActivity, onUris).also {
                    INSTANCE = it
                }
            }

        private fun createInstance(
            componentActivity: ComponentActivity,
            onUris: ActivityResultCallback<List<Uri>>
        ) = componentActivity.registerForActivityResult(
            ActivityResultContracts.PickMultipleVisualMedia(),
            onUris
        )
    }
}