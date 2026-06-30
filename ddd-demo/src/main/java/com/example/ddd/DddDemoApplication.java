package com.example.ddd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * DDD Demo 启动类
 */
@SpringBootApplication
@MapperScan("com.example.ddd.infrastructure.mybatis.mapper")
public class DddDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DddDemoApplication.class, args);
        System.out.println("===========================================");
        System.out.println("DDD Demo Application Started Successfully!");
        System.out.println("Access H2 Console: http://localhost:8080/h2-console");
        System.out.println("API Endpoint: http://localhost:8080/api/orders");
        System.out.println("===========================================");
    }
}
