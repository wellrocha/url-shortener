package com.wellrocha.urlshortener.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(ShortenedUrlNotFoundException::class)
    fun handleShortenedUrlNotFoundException(exception: ShortenedUrlNotFoundException): ResponseEntity<Any> =
        ResponseEntity(mapOf("error" to exception.message), HttpStatus.NOT_FOUND)
}