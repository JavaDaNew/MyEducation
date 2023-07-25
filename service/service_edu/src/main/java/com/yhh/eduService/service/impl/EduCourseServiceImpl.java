package com.yhh.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yhh.eduService.entity.EduCourse;
import com.yhh.eduService.entity.EduCourseDescription;
import com.yhh.eduService.entity.frontvo.CourseFrontVo;
import com.yhh.eduService.entity.frontvo.CourseWebVo;
import com.yhh.eduService.entity.vo.CourseInfoVo;
import com.yhh.eduService.entity.vo.CoursePublishVo;
import com.yhh.eduService.mapper.EduCourseMapper;
import com.yhh.eduService.service.EduChapterService;
import com.yhh.eduService.service.EduCourseDescriptionService;
import com.yhh.eduService.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhh.eduService.service.EduVideoService;
import com.yhh.servicebase.exceptionhandler.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-05-23
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {


    @Autowired  //课程描述的注入
    private EduCourseDescriptionService courseDescriptionService;
    //注入小节
    @Autowired
    private EduVideoService videoService;
    //注入章节
    @Autowired
    private EduChapterService chapterService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
//        1.向课程表中添加课程基本信息  course
//        CourseInfoVo转换成eduCourse
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);  //影响行数
        System.out.println("再来一次为" + insert);
        if (insert == 0){
            throw new MyException(20001,"添加课程失败");
        }
//        获取添加成功后的课程id
        String cid = eduCourse.getId();

//        2.向课程简介表中添加简介  course_description
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
//        手动设置课程描述的id  是mp自动生成的    CourseInfoVo的id需要手动设置 不能自动生成
//        String infoId = courseInfoVo.getId();
//        courseDescription.setId(infoId);
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);
        return cid;
    }
// 根据课程id查询课程信息 再修改
    @Override
    public CourseInfoVo getCourse(String courseId) {
        CourseInfoVo courseInfoVo = new CourseInfoVo();
//        查询课程表
        EduCourse course = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(course,courseInfoVo);
//        查询课程简介
        EduCourseDescription description = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(description.getDescription());

        return courseInfoVo;
    }

    // 修课程信息
    @Override
    public void updateCourse(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int flag = baseMapper.updateById(eduCourse);
        if (flag == 0){
            throw new MyException(20001,"修改课程信息失败");
        }

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        String description = courseInfoVo.getDescription();
        eduCourseDescription.setDescription(description);
        eduCourseDescription.setId(courseInfoVo.getId());
        courseDescriptionService.updateById(eduCourseDescription);
    }

    @Override
    public CoursePublishVo publishCourse(String id) {
//        service调用mapper
//        1. baseMapper
        CoursePublishVo publishCourse = baseMapper.getPublishCourse(id);
        return publishCourse;
    }

// 删除课程
    @Override
    public void removeCourse(String courseId) {
//        总体步骤：1.根据课程id删除小节 —— 2.删除章节 —— 3.删除描述 —— 4.删除课程
        //        1.小节
        videoService.removeVideoByCourseId(courseId);
        //        2.章节
        chapterService.removeChapterByCourseId(courseId);
        //        3.描述
        courseDescriptionService.removeById(courseId);
        //        4.课程本身
        int deleteById = baseMapper.deleteById(courseId);
        if(deleteById == 0){
            throw new MyException(20001,"删除课程失败");
        }

    }


//    消费者前台显示  —— 条件查询课程带分页
    @Override
    public Map<String, Object> getCourseFront(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
//        得到条件参数
        String subjectParentId = courseFrontVo.getSubjectParentId();
        String subjectId = courseFrontVo.getSubjectId();
        String buyCountSort = courseFrontVo.getBuyCountSort(); //销量
        String gmtCreateSort = courseFrontVo.getGmtCreateSort();
        String priceSort = courseFrontVo.getPriceSort();

//       判断条件是否为空  不为空则拼接条件
        if (!StringUtils.isEmpty(subjectParentId)){  //一级分类id
            wrapper.eq("subject_parent_id",subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)){  //二级分类id
            wrapper.eq("subject_id",subjectId);
        }

//        根据关注度 发布时间 价格排序
        if (!StringUtils.isEmpty(buyCountSort)){  //销量
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(buyCountSort)){  //最新发布
            wrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(buyCountSort)){  //价格
            wrapper.orderByAsc("price");
        }
        baseMapper.selectPage(pageParam, wrapper);
        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }


    //        根据课程id  编写sql语句查询课程基本信息 描述
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {

        return baseMapper.getBaseCourseInfo(courseId);
    }

}
