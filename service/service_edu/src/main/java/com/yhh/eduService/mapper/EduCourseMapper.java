package com.yhh.eduService.mapper;

import com.yhh.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yhh.eduService.entity.frontvo.CourseWebVo;
import com.yhh.eduService.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2023-05-23
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

//   根据课程id查询最终要发布的课程信息
    public CoursePublishVo getPublishCourse(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);

}
