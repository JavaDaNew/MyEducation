package com.yhh.eduService.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yhh.commonutils.Result;
import com.yhh.eduService.entity.EduCourse;
import com.yhh.eduService.entity.EduTeacher;
import com.yhh.eduService.service.EduCourseService;
import com.yhh.eduService.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduService/teacherfront")
@CrossOrigin
public class TeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

//    1.分页查询讲师
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public Result getTeacherFrontList(@PathVariable long page,@PathVariable long limit){
        Page<EduTeacher> teacherPage = new Page<>(page,limit);
        Map<String,Object> map = teacherService.getTeacherFront(teacherPage);
//        不用element-ui来做 不能只返回total和records ---> 返回所有数据
//        long total = teacherPage.getTotal();
//        List<EduTeacher> records = teacherPage.getRecords();
        return Result.OK().data(map);
    }

//    2.讲师信息---- 基本信息  所授课程信息
//    2.1 讲师详情
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public Result getTeacherFrontInfo(@PathVariable String teacherId){
        EduTeacher teacher = teacherService.getById(teacherId);

//        课程
        QueryWrapper<EduCourse> courseWrapper = new QueryWrapper<>();
        courseWrapper.eq("teacher_id", teacherId);
        List<EduCourse> coursesList = courseService.list(courseWrapper);
        return Result.OK().data("teacher",teacher).data("course",coursesList);
    }

}
