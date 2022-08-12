package com.buyern.buyern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class BuyernApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuyernApplication.class, args);
    }

}
