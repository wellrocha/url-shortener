package com.wellrocha.urlshortener.model

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable

@DynamoDBTable(tableName = "UrlClick")
class UrlClick (
    @get:DynamoDBHashKey(attributeName = "Id")
    var id: String? = null,

    @get:DynamoDBAttribute(attributeName = "Url")
    var url: String? = null,

    @get:DynamoDBAttribute(attributeName = "Total")
    var total: Int? = null
)

