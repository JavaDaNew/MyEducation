package com.yhh.eduService;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication

//这是一个启动类
//mapper只有接口 没有实现类 -->需要注解mapperScan去扫描到mapper接口
//mapperScan写在启动类和配置类都行--> 最好配置类
@ComponentScan(basePackages = {"com.yhh"})
//组件扫描  如果不加只能扫描当前包 扫描不到其他包
@EnableDiscoveryClient   //nacos注册
@EnableFeignClients     //服务端调用
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
