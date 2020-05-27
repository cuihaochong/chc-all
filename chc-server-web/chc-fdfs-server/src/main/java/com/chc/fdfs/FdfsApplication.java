package com.chc.fdfs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author CuiHaochong
 */
@MapperScan("com.chc.*.mapper")
@EnableEurekaClient
@SpringBootApplication()
public class FdfsApplication {
    public static void main(String[] args) {
        SpringApplication.run(FdfsApplication.class, args);
    }
}
