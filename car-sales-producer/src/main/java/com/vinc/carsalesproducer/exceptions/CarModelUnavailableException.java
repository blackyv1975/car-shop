package com.vinc.carsalesproducer.exceptions;

import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.net.URI;
import java.time.Instant;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ProblemDetail.forStatusAndDetail;

public class CarModelUnavailableException extends ErrorResponseException {

    public CarModelUnavailableException(Integer carModelId, Integer quantity) {
        super(BAD_REQUEST, asProblemDetail("Car model or it's quantity [carModelId = " + carModelId +
                ", quantity = " + quantity + "] is not available at this moment."), null);
    }

    private static ProblemDetail asProblemDetail(String message) {
        ProblemDetail problemDetail = forStatusAndDetail(BAD_REQUEST, message);
        problemDetail.setTitle("Car model unavailable");
        problemDetail.setType(URI.create("https://localhost:8081/api/stocks"));
        problemDetail.setProperty("errorCategory", "Custom handled error");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}
