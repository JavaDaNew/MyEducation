package com.yhh.eduService.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yhh.commonutils.Result;
import com.yhh.commonutils.orderVo.CourseWebVoOrder;
import com.yhh.eduService.entity.EduCourse;
import com.yhh.eduService.entity.EduTeacher;
import com.yhh.eduService.entity.chapter.chapterVo;
import com.yhh.eduService.entity.frontvo.CourseFrontVo;
import com.yhh.eduService.entity.frontvo.CourseWebVo;
import com.yhh.eduService.entity.vo.CourseInfoVo;
import com.yhh.eduService.service.EduChapterService;
import com.yhh.eduService.service.EduCourseService;
import com.yhh.eduService.service.EduTeacherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduService/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

//    1.条件查询带分页 课程
    @PostMapping("getCourseFrontList/{page}/{limit}")
    public Result getTeacherFrontList(@PathVariable long page, @PathVariable long limit,
                                      @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> coursePage = new Page<>(page,limit);
        Map<String,Object> map = courseService.getCourseFront(coursePage,courseFrontVo);

        return Result.OK().data(map);
    }

    //2.课程详情
    @GetMapping("getFrontCourseInfo/{courseId}")
        public Result getFrontCourseInfo(@PathVariable String courseId){
//        根据课程id  编写sql语句查询课程基本信息 描述
         CourseWebVo courseWebVo =  courseService.getBaseCourseInfo(courseId);
        //        根据课程id  查询章节小节
        List<chapterVo> chapterVideo = chapterService.getChapterVideoById(courseId);
        return Result.OK().data("chapterVideo",chapterVideo).data("courseWeb",courseWebVo);
        }

    //    3.根据课程id获取课程信息，便于远程调用—— 在orderService中
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id){
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(id);
        CourseWebVoOrder webVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseWebVo,webVoOrder);
        return webVoOrder;
    }

    }



