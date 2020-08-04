package com.example.maoyan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.example.maoyan.mapper")
public class MaoyanApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaoyanApplication.class, args);
    }

}
