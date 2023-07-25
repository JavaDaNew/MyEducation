package com.yhh.eduService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yhh.eduService.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-04-10
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> getTeacherFront(Page<EduTeacher> teacherPage);
}
