package com.wellrocha.urlshortener.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder
import com.fasterxml.jackson.databind.ObjectMapper
import io.awspring.cloud.messaging.config.QueueMessageHandlerFactory
import io.awspring.cloud.messaging.core.QueueMessagingTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.converter.MessageConverter
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver

@Configuration
class SqsConfig {

    @Value("\${aws.accessKey}")
    lateinit var accessKey: String

    @Value("\${aws.secretKey}")
    lateinit var secretKey: String

    @Value("\${aws.region}")
    lateinit var region: String

    @Bean
    fun queueMessagingTemplate(amazonSQSAsync: AmazonSQSAsync): QueueMessagingTemplate {
        val credentials = BasicAWSCredentials(accessKey, secretKey)
        val amazonSqsAsync = AmazonSQSAsyncClientBuilder
            .standard()
            .withRegion(region)
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .build()

        return QueueMessagingTemplate(amazonSqsAsync)
    }

    @Bean
    fun queueMessageHandlerFactory(mapper: ObjectMapper, amazonSQSAsync: AmazonSQSAsync): QueueMessageHandlerFactory =
        QueueMessageHandlerFactory().apply {
            setAmazonSqs(amazonSQSAsync)
            setArgumentResolvers(
                listOf(
                    PayloadMethodArgumentResolver(jackson2MessageConverter(mapper))
                )
            )
        }

    private fun jackson2MessageConverter(mapper: ObjectMapper): MessageConverter =
        MappingJackson2MessageConverter().apply {
            isStrictContentTypeMatch = false
            objectMapper = mapper
        }
}