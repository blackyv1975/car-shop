#!/usr/bin/env bash

if ! [[ $(docker ps -q -f name=localstack) ]]; then
  echo "WARNING: The localstack Docker container is not running. Please, start it first."
  exit 1
fi

echo
echo "Initializing LocalStack"
echo "======================="

echo
echo "Installing jq"
echo "-------------"
docker exec -t localstack apt-get -y install jq

echo
echo "Creating sales-topic in SNS"
echo "--------------------------"
docker exec -t localstack aws --endpoint-url=http://localhost:4566 sns create-topic --name sales-topic

echo
echo "Creating stock-queue in SQS"
echo "-----------------------------------"
docker exec -t localstack aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name stock-queue

echo
echo "Subscribing stock-queue to sales-topic"
echo "---------------------------------------------"
docker exec -t localstack aws --endpoint-url=http://localhost:4566 sns subscribe \
  --topic-arn arn:aws:sns:eu-west-1:000000000000:sales-topic \
  --protocol sqs \
  --notification-endpoint arn:aws:sqs:eu-west-1:000000000000:stock-queue

echo
echo "LocalStack initialized successfully"
echo "==================================="
echo