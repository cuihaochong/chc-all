package com.chc;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
@MapperScan("com.chc.mapper")
@SpringBootApplication
@EnableDistributedTransaction
public class LcnServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LcnServerApplication.class, args);
    }

}
