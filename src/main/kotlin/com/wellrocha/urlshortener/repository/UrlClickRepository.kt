package com.wellrocha.urlshortener.repository

import com.wellrocha.urlshortener.model.UrlClick
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository
import org.socialsignin.spring.data.dynamodb.repository.EnableScan

@EnableScan
interface UrlClickRepository : DynamoDBCrudRepository<UrlClick, String>