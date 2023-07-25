package com.yhh.eduService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yhh.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yhh.eduService.entity.frontvo.CourseFrontVo;
import com.yhh.eduService.entity.frontvo.CourseWebVo;
import com.yhh.eduService.entity.vo.CourseInfoVo;
import com.yhh.eduService.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-05-23
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourse(String courseId);

    void updateCourse(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourse(String id);

    void removeCourse(String courseId);


    Map<String, Object> getCourseFront(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo);


    CourseWebVo getBaseCourseInfo(String courseId);

}
