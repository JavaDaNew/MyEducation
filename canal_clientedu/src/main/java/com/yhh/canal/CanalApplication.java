package com.yhh.canal;

import com.yhh.canal.client.CanalClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.annotation.Resource;
import java.util.PriorityQueue;

@SpringBootApplication
@ComponentScan(basePackages = "com.yhh")
@CrossOrigin
public class CanalApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(CanalApplication.class, args);

    }

    @Resource
    private CanalClient canalClient;

    @Override
    public void run(String... strings) throws Exception {
        //项目启动，执行canal客户端监听
        canalClient.run();
    }

}