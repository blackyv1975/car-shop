package com.vinc.carstockconsumer.consumer;

import com.vinc.carstockconsumer.config.property.AwsProperties;
import com.vinc.carstockconsumer.mapper.SalesOrderMapper;
import com.vinc.carstockconsumer.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

@Slf4j
@RequiredArgsConstructor
@Component
public class SalesOrderConsumer {

    private final SqsClient sqsClient;
    private final AwsProperties awsProperties;
    private final SalesOrderMapper salesOrderMapper;
    private final StockRepository stockRepository;


    @Scheduled(fixedRate = 3000)
    public void scheduledConsumer() {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(awsProperties.getSqs().getQueueUrl())
                .build();

        sqsClient.receiveMessage(receiveMessageRequest)
                .messages()
                .forEach(message -> {
                    var salesOrderEvent = salesOrderMapper.toSalesOrderEvent(message);
                    log.info("Received Sales Order Event: {}", salesOrderEvent);

                    var carStock = stockRepository.findById(Integer.parseInt(salesOrderEvent.getCarModelId())).orElseThrow();
                    var prevQty = carStock.getQuantity();
                    log.info("Previous {} stock quantity: {}", carStock.getCarModel(), prevQty);
                    var salesQty = Integer.parseInt(salesOrderEvent.getQty());
                    carStock.setQuantity(prevQty - salesQty);
                    var actualCarStock = stockRepository.save(carStock);
                    log.info("Actual {} stock quantity: {}", actualCarStock.getCarModel(), actualCarStock.getQuantity());

                    DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                            .queueUrl(awsProperties.getSqs().getQueueUrl())
                            .receiptHandle(message.receiptHandle())
                            .build();

                    sqsClient.deleteMessage(deleteMessageRequest);
                });
    }
}
