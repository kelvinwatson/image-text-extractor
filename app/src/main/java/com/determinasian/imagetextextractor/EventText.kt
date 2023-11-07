package com.determinasian.imagetextextractor

data class EventText(
    val name: String,
    val dateRange: String,
    val venue: String? = null,
    val address: String? = null,
)
