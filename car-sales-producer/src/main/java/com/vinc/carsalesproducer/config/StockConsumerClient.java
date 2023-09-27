package com.vinc.carsalesproducer.config;

import com.vinc.carsalesproducer.domain.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class StockConsumerClient {

    private final RestTemplate restTemplate;

    @Value("${car-stock-consumer.url}")
    private String stockConsumerUrl;

    public List<Stock> getStocks() {
        ParameterizedTypeReference<List<Stock>> parameterizedTypeReference = new ParameterizedTypeReference<>() {};
        try {
            ResponseEntity<List<Stock>> responseEntity = restTemplate.exchange(
                    stockConsumerUrl + "/api/stocks", HttpMethod.GET, null, parameterizedTypeReference);
            return responseEntity.getBody();
        } catch (Exception e) {
            log.error("An exception happened while calling car-stock-consumer. Error message: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

}
