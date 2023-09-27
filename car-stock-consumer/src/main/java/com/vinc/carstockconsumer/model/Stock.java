package com.vinc.carstockconsumer.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

@Data
@AllArgsConstructor
@NoArgsConstructor
@KeySpace("stocks")
public class Stock {

    @Id
    private Integer id;
    private String carModel;
    private Integer quantity;
}
