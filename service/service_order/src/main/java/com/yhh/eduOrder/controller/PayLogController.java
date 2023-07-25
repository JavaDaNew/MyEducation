package com.yhh.eduOrder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.yhh.commonutils.Result;
import com.yhh.eduOrder.entity.Order;
import com.yhh.eduOrder.entity.PayLog;
import com.yhh.eduOrder.service.PayLogService;
import org.apache.poi.hssf.record.DVALRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-06-28
 */
@RestController
@RequestMapping("/eduOrder/pay-log")
@CrossOrigin
public class PayLogController {


    @Autowired
    private PayLogService payLogService;

//    1.生成微信支付的二维码 根据订单号知道支付课程 价格
    @GetMapping("createNative/{orderNo}")
    public Result createNative(@PathVariable String orderNo){
        Map map = payLogService.createNative(orderNo);
        return Result.OK().data(map);
    }
    //    2.查询订单支付状态
    @GetMapping("queryPaySate/{orderNo}")
    public Result queryPaySate(@PathVariable String orderNo){
        Map<String,String> map = payLogService.queryPaySate(orderNo);
        if (map == null){
            return Result.Fail().message("支付失败");
        }
        if (map.get("trade_state").equals("SUCCESS")) {//如果成功
                //更改订单状态
            payLogService.updateOrderStatus(map);
            return Result.OK().message("支付成功");
        }

        return Result.OK().code(25000).message("支付中...");
    }


}

