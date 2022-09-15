package com.wellrocha.urlshortener.listener

import com.wellrocha.urlshortener.dto.UrlClickEvent
import com.wellrocha.urlshortener.model.UrlClick
import com.wellrocha.urlshortener.repository.UrlClickRepository
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy
import io.awspring.cloud.messaging.listener.annotation.SqsListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Service

private const val QUEUE_NAME = "url_shortener_click"

@Service
class SaveUrlClickListener(
    private val urlClickRepository: UrlClickRepository
) {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(SaveUrlClickListener::class.java)
    }

    @SqsListener(value = [QUEUE_NAME], deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    fun receiveMessage(
        message: UrlClickEvent,
        @Header("SenderId") senderId: String,
    ) {
        logger.info("A new message was received [{}]", message)

        urlClickRepository.findByIdOrNull(message.payload.id)?.let {
            it.apply {
                id = message.payload.id
                url = message.payload.url
                total = it.total?.plus(1)
            }
            urlClickRepository.save(it)

            logger.info("Url click was updated")
        } ?: UrlClick(id = message.payload.id, url = message.payload.url, total =  1).let {
            urlClickRepository.save(it)

            logger.info("Url click was saved")
        }
    }
}
