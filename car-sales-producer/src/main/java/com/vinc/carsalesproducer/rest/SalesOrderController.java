package com.vinc.carsalesproducer.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinc.carsalesproducer.config.StockConsumerClient;
import com.vinc.carsalesproducer.config.property.AwsProperties;
import com.vinc.carsalesproducer.event.StockEvent;
import com.vinc.carsalesproducer.exceptions.CarModelUnavailableException;
import com.vinc.carsalesproducer.rest.dto.SalesOrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sales")
public class SalesOrderController {

    private final StockConsumerClient stockConsumerClient;
    private final SnsClient snsClient;
    private final AwsProperties awsProperties;
    private final ObjectMapper objectMapper;

    @PostMapping
    public void placeSalesOrder(@RequestBody SalesOrderDto salesOrderDto) {
        if (stockConsumerClient.getStocks().stream()
                .filter(s -> Objects.equals(s.id(), salesOrderDto.getCarModelId()))
                .anyMatch(q -> q.quantity() >= salesOrderDto.getQty())) {
            log.info("New Sales Order successfully placed: {}", salesOrderDto);
            snsClient.publish(
                    PublishRequest.builder()
                            .topicArn(awsProperties.getSns().getTopicArn())
                            .message(toJson(StockEvent.of(salesOrderDto.getCarModelId().toString(), salesOrderDto.getQty().toString())))
                            .build()
            );
        } else {
            throw new CarModelUnavailableException(salesOrderDto.getCarModelId(), salesOrderDto.getQty());
        }


    }

    private String toJson(StockEvent stockEvent) {
        try {
            return objectMapper.writeValueAsString(stockEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
