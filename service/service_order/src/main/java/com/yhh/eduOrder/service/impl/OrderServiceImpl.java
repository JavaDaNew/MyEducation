package com.yhh.eduOrder.service.impl;

import com.yhh.commonutils.orderVo.CourseWebVoOrder;
import com.yhh.commonutils.orderVo.UcenterMemberOrder;
import com.yhh.eduOrder.client.EduClient;
import com.yhh.eduOrder.client.UcenterClient;
import com.yhh.eduOrder.entity.Order;
import com.yhh.eduOrder.mapper.OrderMapper;
import com.yhh.eduOrder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhh.eduOrder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-06-28
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;
    //    1. 生成订单
    @Override
    public String cerateOrders(String courseId, String memberId) {
//        通过远程调用获取用户信息
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);

//        通过远程调用获取课程信息
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

//        取值 添加到数据库
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());   //订单号  随机唯一的就行 ：工具类或者UUID
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);   // 0未支付 1 支付
        order.setPayType(1);  //支付类型 微信1
        baseMapper.insert(order);
//                返回订单号
        return order.getOrderNo();
    }
}
