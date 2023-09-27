package com.vinc.carstockconsumer.event;


import lombok.Value;

@Value(staticConstructor = "of")
public class SalesOrderEvent {

    String carModelId;
    String qty;
}
