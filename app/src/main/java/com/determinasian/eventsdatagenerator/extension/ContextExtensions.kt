package com.determinasian.eventsdatagenerator.extension

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity

tailrec fun Context.findComponentActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> {
        this
    }

    is ContextWrapper -> {
        baseContext.findComponentActivity()
    }

    else -> {
        null
    }
}