package com.yhh.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yhh.commonutils.Result;
import com.yhh.staservice.client.UcenterClient;
import com.yhh.staservice.entity.StatisticsDaily;
import com.yhh.staservice.mapper.StatisticsDailyMapper;
import com.yhh.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.bytebuddy.asm.Advice;
import org.apache.commons.lang3.RandomUtils;
import org.bouncycastle.cms.PasswordRecipientId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-06-29
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
// 调用UcenterClient

    @Autowired
    private UcenterClient ucenterClient;
    @Override
    public void registerCount(String day) {

        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);
        Result registerR = ucenterClient.countRegister(day);
        Integer countRegister = (Integer)registerR.getData().get("countRegister");
//        添加到数据库
        StatisticsDaily statistics = new StatisticsDaily();
        statistics.setRegisterNum(countRegister);
        statistics.setDateCalculated(day);
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        statistics.setLoginNum(loginNum);
        statistics.setVideoViewNum(videoViewNum);
        statistics.setCourseNum(courseNum);
        baseMapper.insert(statistics);
    }

    //    2.图表显示，返回两部分数据，日期json数组，数量json数组
    @Override
    public Map<String, Object> getshowData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);
        List<String> date_calculatedList = new ArrayList<>();
        List<Integer> numDateList = new ArrayList<>();

        for (int i = 0; i < staList.size(); i++) {
            StatisticsDaily daily = staList.get(i);
            date_calculatedList.add(daily.getDateCalculated());
            switch (type) {
                case "register_num":
                    numDateList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    numDateList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    numDateList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numDateList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("date_calculatedList",date_calculatedList);
        map.put("numDateList",numDateList);
        return map;
    }


}
