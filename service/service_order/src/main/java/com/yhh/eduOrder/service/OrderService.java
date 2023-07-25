package com.yhh.eduOrder.service;

import com.yhh.eduOrder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-06-28
 */
public interface OrderService extends IService<Order> {

    String cerateOrders(String courseId, String memberId);
}
