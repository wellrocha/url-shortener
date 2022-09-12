package com.wellrocha.urlshortener.repository

import com.wellrocha.urlshortener.model.ShortenedUrl
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ShortenedUrlRepository : CrudRepository<ShortenedUrl, String>

