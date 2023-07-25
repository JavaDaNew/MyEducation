package com.yhh.eduService.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonMerge;
import com.yhh.commonutils.Result;
import com.yhh.eduService.entity.EduCourse;
import com.yhh.eduService.entity.EduTeacher;
import com.yhh.eduService.entity.EduVideo;
import com.yhh.eduService.service.EduCourseService;
import com.yhh.eduService.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eduService/indexfront")
@CrossOrigin
public class IndexFrontController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;

//    查询前8条热门课程，前4条热门讲师
    @GetMapping("index")
    public Result index(){
//        课程
        QueryWrapper<EduCourse> courseWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("id");
        courseWrapper.last("limit 8");
        List<EduCourse> courseList = courseService.list(courseWrapper);

//        讲师
        QueryWrapper<EduTeacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.orderByAsc("id");
        teacherWrapper.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(teacherWrapper);

        return Result.OK().data("course",courseList).data("teacher",teacherList);
    }

}
