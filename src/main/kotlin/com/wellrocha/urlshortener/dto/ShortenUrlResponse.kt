package com.wellrocha.urlshortener.dto

import java.util.*

data class ShortenUrlResponse(
    val id: String, val url: String, val createdAt: Date
)
