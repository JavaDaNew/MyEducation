package com.yhh.eduService.service;

import com.yhh.eduService.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yhh.eduService.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-05-18
 */
public interface EduSubjectService extends IService<EduSubject> {
//添加课程分类
    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    List<OneSubject> getOneTwoSubject();

}
