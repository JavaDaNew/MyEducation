package com.yhh.eduOrder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yhh.commonutils.JwtUtils;
import com.yhh.commonutils.Result;
import com.yhh.eduOrder.entity.Order;
import com.yhh.eduOrder.service.OrderService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-06-28
 */
@RestController
@RequestMapping("/eduOrder/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

//    1. 生成订单
    @PostMapping("cerateOrder/{courseId}")
    public Result saveOrder(@PathVariable String courseId, HttpServletRequest request){
//        request得到用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
//        创建订单  返回订单号
        String orderNo = orderService.cerateOrders(courseId,memberId);

        return Result.OK().data("orderId",orderNo);
    }

    //    2. 根据订单号id查询订单信息
    @GetMapping("getOrderInfo/{orderId}")
    public Result getOrderInfo(@PathVariable String orderId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return Result.OK().data("item",order);


    }

}

