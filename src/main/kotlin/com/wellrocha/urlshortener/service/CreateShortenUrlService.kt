package com.wellrocha.urlshortener.service

import com.wellrocha.urlshortener.dto.ShortenUrlRequest
import com.wellrocha.urlshortener.dto.ShortenUrlResponse
import com.wellrocha.urlshortener.model.ShortenedUrl
import com.wellrocha.urlshortener.repository.ShortenedUrlRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreateShortenUrlService(
    private val repository: ShortenedUrlRepository,
) {
    fun execute(request: ShortenUrlRequest): ShortenUrlResponse {
        val shortenedUrl = ShortenedUrl(request.id, request.url, Date())
        repository.save(shortenedUrl)
        return ShortenUrlResponse(request.id)
    }
}