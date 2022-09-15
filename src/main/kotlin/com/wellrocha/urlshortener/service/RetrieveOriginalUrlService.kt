package com.wellrocha.urlshortener.service

import com.wellrocha.urlshortener.exceptions.ShortenedUrlNotFoundException
import com.wellrocha.urlshortener.repository.ShortenedUrlRepository
import io.awspring.cloud.messaging.core.QueueMessagingTemplate
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service

@Service
class RetrieveOriginalUrlService(
    private val shortenedUrlRepository: ShortenedUrlRepository,
    private val queueMessagingTemplate: QueueMessagingTemplate,
) {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(RetrieveOriginalUrlService::class.java)
    }

    @Value("\${aws.sqs.url_shortener_click_endpoint}")
    lateinit var queueEndpoint: String

    fun execute(id: String): String  {
        val shortenedUrl = shortenedUrlRepository
            .findById(id)
            .orElseThrow { ShortenedUrlNotFoundException("Url Not Found For Id: $id") }

        val payload = MessageBuilder
            .withPayload(shortenedUrl)
            .build()
        queueMessagingTemplate.convertAndSend(queueEndpoint, payload)

        logger.info("Retrieved original url with success [{}]", shortenedUrl.url)
        return shortenedUrl.url
    }
}