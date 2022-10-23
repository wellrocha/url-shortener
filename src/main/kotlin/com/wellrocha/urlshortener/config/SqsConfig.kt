package com.wellrocha.urlshortener.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder
import com.fasterxml.jackson.databind.ObjectMapper
import io.awspring.cloud.messaging.config.QueueMessageHandlerFactory
import io.awspring.cloud.messaging.core.QueueMessagingTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
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

    @Value("\${aws.endpoint}")
    lateinit var endpoint: String

    @Bean
    @Primary
    fun amazonSQSAsync(): AmazonSQSAsync {
        val credentials = BasicAWSCredentials(accessKey, secretKey)
        return AmazonSQSAsyncClientBuilder
            .standard()
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(endpoint, region))
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .build()
    }

    @Bean
    fun queueMessagingTemplate(amazonSQSAsync: AmazonSQSAsync): QueueMessagingTemplate {
        return QueueMessagingTemplate(amazonSQSAsync())
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