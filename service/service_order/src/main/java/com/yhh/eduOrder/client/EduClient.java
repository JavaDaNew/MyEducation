package com.yhh.eduOrder.client;

import com.yhh.commonutils.orderVo.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service_edu")
public interface EduClient {

    //    3.根据课程id获取课程信息，便于远程调用—— 在orderService中
    @PostMapping("/eduService/coursefront/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable("id") String id);
}
