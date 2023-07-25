package com.yhh.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Deque;
import java.util.LinkedList;

@SpringBootApplication
@ComponentScan(basePackages = {"com.yhh"})
@EnableDiscoveryClient   //nacos的注册中心
@EnableFeignClients         //远程调用
@MapperScan("com.yhh.staservice.mapper")
@EnableScheduling
public class StaApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class,args);


    }
}
