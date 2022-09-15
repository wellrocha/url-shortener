package com.wellrocha.urlshortener.dto

import java.util.*

data class Payload(val id: String, val url: String, val createdAt: Date)
data class UrlClickEvent(val payload: Payload)