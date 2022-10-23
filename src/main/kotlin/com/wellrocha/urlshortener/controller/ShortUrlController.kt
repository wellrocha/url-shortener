package com.wellrocha.urlshortener.controller

import com.wellrocha.urlshortener.dto.ShortenUrlRequest
import com.wellrocha.urlshortener.service.CreateShortenUrlService
import com.wellrocha.urlshortener.service.RetrieveOriginalUrlService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
@RequestMapping("url-shortener")
class ShortUrlController(
    private val createShortenUrlService: CreateShortenUrlService,
    private val retrieveOriginalUrlService: RetrieveOriginalUrlService
) {
    @PostMapping
    fun shortenUrl(@RequestBody @Valid request: ShortenUrlRequest, uriBuilder: UriComponentsBuilder):
            ResponseEntity<Map<String, String>> = with(createShortenUrlService.execute(request)) {
        ResponseEntity
            .ok()
            .body(mapOf("url" to uriBuilder.path("/url-shortener/{id}").buildAndExpand(id).toString()))
    }

    @GetMapping("/{id}")
    fun redirect(@PathVariable id: String, response: HttpServletResponse) =
        with(retrieveOriginalUrlService.execute(id)) {
            response.sendRedirect(this)
        }
}
