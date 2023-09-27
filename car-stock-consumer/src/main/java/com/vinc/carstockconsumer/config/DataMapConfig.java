package com.vinc.carstockconsumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.keyvalue.core.KeyValueAdapter;
import org.springframework.data.map.MapKeyValueAdapter;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class DataMapConfig {

    @Bean
    public KeyValueAdapter keyValueAdapter() {
        return new MapKeyValueAdapter(ConcurrentHashMap.class);
    }

}
