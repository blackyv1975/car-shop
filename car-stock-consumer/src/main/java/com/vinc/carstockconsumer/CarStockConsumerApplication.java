package com.vinc.carstockconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.map.repository.config.EnableMapRepositories;

@EnableMapRepositories
@SpringBootApplication
public class CarStockConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarStockConsumerApplication.class, args);
    }

}
