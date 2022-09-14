package com.wellrocha.urlshortener.controller

import com.wellrocha.urlshortener.dto.ShortenUrlRequest
import com.wellrocha.urlshortener.dto.ShortenUrlResponse
import com.wellrocha.urlshortener.service.CreateShortenUrlService
import com.wellrocha.urlshortener.service.RetrieveOriginalUrlService
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
@RequestMapping("url-shortener")
class ShortUrlController(
    private val createShortenUrlService: CreateShortenUrlService,
    private val retrieveOriginalUrlService: RetrieveOriginalUrlService
) {
    @PostMapping
    fun shortenUrl(@RequestBody @Valid request: ShortenUrlRequest): ShortenUrlResponse {
        return createShortenUrlService.execute(request)
    }

    @GetMapping("/{id}")
    fun redirect(@PathVariable id: String, response: HttpServletResponse) {
        val url = retrieveOriginalUrlService.execute(id)
        response.sendRedirect(url)
    }
}
