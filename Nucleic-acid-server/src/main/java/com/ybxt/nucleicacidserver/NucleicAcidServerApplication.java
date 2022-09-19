package com.ybxt.nucleicacidserver;

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
@EnableMethodCache(basePackages = "com.ybxt.nucleicacid.service")
public class NucleicAcidServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NucleicAcidServerApplication.class, args);
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
