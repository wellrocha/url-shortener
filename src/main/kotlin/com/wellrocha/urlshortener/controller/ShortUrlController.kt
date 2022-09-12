package com.wellrocha.urlshortener.controller

import com.wellrocha.urlshortener.dto.ShortenUrlRequest
import com.wellrocha.urlshortener.dto.ShortenUrlResponse
import com.wellrocha.urlshortener.services.CreateShortenUrlService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("url-shortener")
class ShortUrlController(
    val createShortenUrlService: CreateShortenUrlService
) {
    @PostMapping
    fun shortenUrl(@RequestBody @Valid request: ShortenUrlRequest): ShortenUrlResponse {
        return createShortenUrlService.execute(request)
    }
}
