package com.enterprise.exchange;

import com.enterprise.exchange.config.ExchangeProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ExchangeProperties.class)
public class EnterpriseDmzExchangeApplication {
    public static void main(String[] args) {
        SpringApplication.run(EnterpriseDmzExchangeApplication.class, args);
    }
}
