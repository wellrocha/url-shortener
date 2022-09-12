package com.wellrocha.urlshortener.dto

import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets
import javax.validation.constraints.NotEmpty

data class ShortenUrlRequest(@get:NotEmpty val url: String) {
    val id: String
    get() {
        return Hashing.murmur3_32_fixed().hashString(this.url, StandardCharsets.UTF_8).toString()
    }
}