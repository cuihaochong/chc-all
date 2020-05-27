package com.chc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Description: 启动类
 *
 * @author cuihaochong
 * @date 2019/12/12
 */
@EnableEurekaClient
@EnableFeignClients("com.chc.client")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SeataServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeataServerApplication.class, args);
    }

}
