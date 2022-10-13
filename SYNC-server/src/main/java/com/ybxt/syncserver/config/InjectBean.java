package com.ybxt.syncserver.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Slf4j
public class InjectBean {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        log.debug("init-restTemplate");
        return new RestTemplate();
    }
}
