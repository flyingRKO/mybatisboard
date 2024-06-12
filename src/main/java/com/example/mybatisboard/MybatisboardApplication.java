package com.example.mybatisboard;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.mybatisboard.mapper")
public class MybatisboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisboardApplication.class, args);
    }

}
