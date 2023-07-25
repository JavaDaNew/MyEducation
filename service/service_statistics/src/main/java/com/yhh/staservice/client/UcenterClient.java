package com.yhh.staservice.client;

import com.yhh.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
//指定调用的服务名字
public interface UcenterClient {
    @GetMapping("/ucenterservice/member/countRegister/{day}")
    public Result countRegister(@PathVariable("day") String day);
}
