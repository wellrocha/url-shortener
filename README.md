# URL Shortener
This is a URL Shortener written in Kotlin with Spring Boot, Redis, SQS and DynamoDB

## Getting Started
### Prerequisites
* Maven
* Java 17
* Kotlin 1.6
* Docker
* AWS SDK

```
# Environment Variables

AWS_ACCESS_KEY=
AWS_ENDPOINT=http://localhost:4566
AWS_REGION=us-east-1
AWS_SECRET_KEY=
AWS_SQS_URL_SHORTENER_CLICK_ENDPOINT=
REDIS_HOST=localhost
REDIS_PORT=6379

# Docker

$ docker-compose up -d

# LocalStack

$ aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name url_shortener_click
$ aws --endpoint-url=http://localhost:4566 dynamodb create-table \
    --table-name UrlClick \
    --attribute-definitions \
        AttributeName=Id,AttributeType=S \
    --key-schema \
        AttributeName=Id,KeyType=HASH \
    --provisioned-throughput \
        ReadCapacityUnits=5,WriteCapacityUnits=5 \
    --table-class STANDARD
```

## Usage
```
# Creating Shortened URL

$ curl --location --request POST 'http://localhost:8080/url-shortener' \
--header 'Content-Type: application/json' \
--data-raw '{
    "url": "https://www.gamespot.com/articles/gta-6-leak-rockstar-responds-says-work-will-continue-as-planned/1100-6507632/"
}'

# Accessing Long URL

http://localhost:8080/url-shortener/a31bb106

# Checking Saved Clicks

$ aws --endpoint-url=http://localhost:4566 dynamodb execute-statement --statement "SELECT * FROM UrlClick"
```