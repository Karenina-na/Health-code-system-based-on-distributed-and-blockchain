package com.ybxt.traceserver;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "com.ybxt.traceserver.service")
public class TraceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TraceServerApplication.class, args);
    }


    /**
     * 负载均衡
     * @return  RestTemplate
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
