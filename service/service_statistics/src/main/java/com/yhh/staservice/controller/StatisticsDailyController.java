package com.yhh.staservice.controller;


import com.yhh.commonutils.Result;
import com.yhh.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 统计某一天的注册人数
 * ucenter中写接口查询某一天的注册人数 sta中远程调用得到数据，并加入统计表中
 * </p>
 *
 * @author testjava
 * @since 2023-06-29
 */
@RestController
@RequestMapping("/staservice/statistics-daily")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService staService;
//    1.统计某一天的注册人数 并加到数据库
    @PostMapping("registerCount/{day}")
    public Result registerCount(@PathVariable String day){
        staService.registerCount(day);
        return Result.OK();
    }

    //    2.图表显示，返回两部分数据，日期json数组，数量json数组
    @GetMapping("showData/{type}/{begin}/{end}")
    public Result showData(@PathVariable String type,@PathVariable String begin,
                           @PathVariable String end){
        Map<String,Object> map = staService.getshowData(type,begin,end);
        return Result.OK().data(map);
    }
}

