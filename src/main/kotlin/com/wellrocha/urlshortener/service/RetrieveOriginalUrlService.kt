package com.wellrocha.urlshortener.service

import com.wellrocha.urlshortener.exceptions.ShortenedUrlNotFoundException
import com.wellrocha.urlshortener.repository.ShortenedUrlRepository
import org.springframework.stereotype.Service

@Service
class RetrieveOriginalUrlService(
    private val shortenedUrlRepository: ShortenedUrlRepository,
) {
    fun execute(id: String) = shortenedUrlRepository
        .findById(id)
        .orElseThrow { ShortenedUrlNotFoundException("Url Not Found For Id: $id") }
        .url
}