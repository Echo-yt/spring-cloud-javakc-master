package com.zhou.javakc.ccds.card;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author yang
 * @version v0.0.1
 * @date 2019/10/19 10:56
 */
@SpringBootApplication
@EnableEurekaClient
@EntityScan("com.zhou.javakc.component.data.entity.*")
@ComponentScan(basePackages={"com.zhou.javakc.component.data.entity.config", "com.zhou.javakc.ccds.card"})

public class CcdsCardApplication {

    public static void main(String[] args) {
        SpringApplication.run(CcdsCardApplication.class, args);
    }

}
