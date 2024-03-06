package com.neillon.events.data.model

data class EventImageDto(
    val ratio: String,
    val url: String,
    val width: Int,
    val height: Int,
    val fallback: Boolean
)