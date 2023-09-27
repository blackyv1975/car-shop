package com.vinc.carsalesproducer.event;


import lombok.Value;

@Value(staticConstructor = "of")
public class StockEvent {

    String carModelId;
    String qty;
}
