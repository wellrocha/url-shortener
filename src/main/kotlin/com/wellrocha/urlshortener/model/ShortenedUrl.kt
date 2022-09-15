package com.wellrocha.urlshortener.model

import org.springframework.data.redis.core.RedisHash
import java.util.*

@RedisHash("ShortenedUrl")
data class ShortenedUrl(
    val id: String, val url: String, val createdAt: Date
)