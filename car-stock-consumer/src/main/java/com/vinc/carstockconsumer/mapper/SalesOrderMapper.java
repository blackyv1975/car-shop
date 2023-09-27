package com.vinc.carstockconsumer.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinc.carstockconsumer.event.SalesOrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.model.Message;

@RequiredArgsConstructor
@Component
public class SalesOrderMapper {

    private final ObjectMapper objectMapper;

    public SalesOrderEvent toSalesOrderEvent(Message message) {
        try {
            MessageBody messageBody = objectMapper.readValue(message.body(), MessageBody.class);
            return objectMapper.readValue(messageBody.message(), SalesOrderEvent.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private record MessageBody(@JsonProperty("Message") String message) {
    }
}
