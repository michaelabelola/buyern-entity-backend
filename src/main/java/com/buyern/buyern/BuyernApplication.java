package com.buyern.buyern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@SpringBootApplication
@EnableRedisRepositories
public class BuyernApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuyernApplication.class, args);
    }

}
