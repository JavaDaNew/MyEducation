package com.yhh.staservice.service;

import com.yhh.commonutils.Result;
import com.yhh.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-06-29
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void registerCount(String day);


    Map<String, Object> getshowData(String type, String begin, String end);
}
