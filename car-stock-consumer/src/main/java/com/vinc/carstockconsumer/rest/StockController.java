package com.vinc.carstockconsumer.rest;

import com.vinc.carstockconsumer.model.Stock;
import com.vinc.carstockconsumer.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockRepository stockRepository;

    @GetMapping
    public Iterable<Stock> getStocks() {
        return stockRepository.findAll();
    }

    @GetMapping("/populate")
    public Iterable<Stock> populateStock() {
        var stocks = List.of(
                new Stock(1, "Toyota", 100),
                new Stock(2, "Ford", 100),
                new Stock(3, "Volkswagen", 100));
        stockRepository.saveAll(stocks);
        return stockRepository.findAll();
    }
}
