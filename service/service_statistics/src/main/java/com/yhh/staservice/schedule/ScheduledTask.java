package com.yhh.staservice.schedule;


import com.yhh.staservice.service.StatisticsDailyService;
import com.yhh.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {
    @Autowired
    private StatisticsDailyService staService;
//    每天凌晨一点 把前一天的数据查询添加
    @Scheduled(cron = "0 0 1 * * ? *")
    public void task1(){
        staService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }
}
