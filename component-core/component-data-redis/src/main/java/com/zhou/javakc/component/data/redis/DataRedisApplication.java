package com.zhou.javakc.component.data.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yang
 * @version v0.0.1
 * @date 2019/11/1 20:27
 */
@SpringBootApplication
public class DataRedisApplication {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active","redis");
        SpringApplication.run(DataRedisApplication.class,args);
    }
}
