package com.yhh.eduService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yhh.commonutils.Result;
import com.yhh.eduService.entity.EduCourse;
import com.yhh.eduService.entity.vo.CourseInfoVo;
import com.yhh.eduService.entity.vo.CoursePublishVo;
import com.yhh.eduService.entity.vo.CourseQuery;
import com.yhh.eduService.entity.vo.CourseQuery;
import com.yhh.eduService.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-05-23
 */
@RestController
@RequestMapping("/eduService/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;

//     TODO 课程列表接口 -- 完善条件查询带分页
//    查询所有课程
    @GetMapping("getAllCourse")
    public Result getAllCourse(){
        List<EduCourse> courseList = courseService.list(null);
        return Result.OK().data("list",courseList);
    }
    //添加课程基本方法
    @PostMapping("addCourseInfo")
    public Result addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.saveCourseInfo(courseInfoVo);
//        返回添加之后的课程id，为了添加课程大纲
        String id = courseService.saveCourseInfo(courseInfoVo);

        return  Result.OK().data("courseId",id);
    }


//    根据课程id查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public Result getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourse(courseId);
        return Result.OK().data("courseInfoVo",courseInfoVo);
    }

//    修改课程信息
    @PostMapping("updateCourseInfo")
    public Result updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourse(courseInfoVo);
        return Result.OK();
    }

    //   根据课程id查询最终要发布的课程信息
    @GetMapping("getPublishCourse/{id}")
    public Result getPublishCourse(@PathVariable String id){
        CoursePublishVo coursePublishVo = courseService.publishCourse(id);
        return Result.OK().data("coursePublish",coursePublishVo);
    }

//    课程最终发布：修改课程状态 Draft-> Normal
    @PostMapping("finallyPublish/{id}")
    public Result finallyPublish(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
//        修改课程状态 Draft-> Normal
        courseService.updateById(eduCourse);
        return Result.OK();
    }

//    删除课程
    @DeleteMapping("deleteCourse/{id}")
    public Result deleteCourse(@PathVariable String id){
         courseService.removeCourse(id);
         return Result.OK();
    }

        //4. 条件查询带分页
        @ApiOperation("4.条件查询带分页")
        @PostMapping("PageCourseCondition/{cur}/{limit}")
        public Result PageCourseCondition(@PathVariable long cur,
                                           @PathVariable long limit,
                                           @RequestBody(required = false) CourseQuery courseQuery
        )
    /**
     *  @RequestBody -> 使用json传递数据时，会把数据封装到对象里面 （网页提交 传给后端）
     *  使用POST提交
     *  required = false -> 参数值可以为空
     *  @ResponseBody -> 返回json数据
     */
    {
        // 查询条件用QuerryWrapper对象 调用里面的方法即可
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //方法如ge le eq ne between like 等
        // 动态sql
        //判断条件是否为空 如果不为空则拼接条件

        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        String begin = courseQuery.getBegin();
        String end = courseQuery.getEnd();;

        if(!StringUtils.isEmpty(title)){
            //构建条件
            wrapper.like("title",title);//表里字段名 传入的值
        }
        if (!StringUtils.isEmpty(status)){
            wrapper.eq("status",status);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_modified",end);
        }

        //按照时间排序
//        wrapper.orderByAsc("gmt_create");
        //调用方法获得分页数据
        Page<EduCourse> pageParam = new Page<>(cur,limit);
        courseService.page(pageParam,wrapper);
        long total = pageParam.getTotal();
        List<EduCourse> courses = pageParam.getRecords();
//      传递数据 用统一结果返回
//        HashMap<Object, Object> map = new HashMap<>();
//        map.put("total",total);
//        map.put("rows",records);
//        return Result.OK().data(map);
        return Result.OK().data("total",total).data("courses",courses);
    }

}

