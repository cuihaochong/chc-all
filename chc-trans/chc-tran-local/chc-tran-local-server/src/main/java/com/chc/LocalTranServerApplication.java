package com.chc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Description: 启动类
 *
 * @author cuihaochong
 * @date 2019/12/12
 */
@MapperScan("com.chc.mapper")
@SpringBootApplication
public class LocalTranServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocalTranServerApplication.class, args);
    }

}
