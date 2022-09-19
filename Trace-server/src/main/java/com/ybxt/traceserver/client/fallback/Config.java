package com.ybxt.traceserver.client.fallback;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public PersonDataClientFallback orderClientFallBack() {
        return new PersonDataClientFallback();
    }
}
