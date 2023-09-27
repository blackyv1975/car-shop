package com.vinc.carstockconsumer.repository;

import com.vinc.carstockconsumer.model.Stock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends CrudRepository<Stock, Integer> {

}