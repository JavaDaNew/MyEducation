package com.yhh.eduService.service;

import com.yhh.eduService.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yhh.eduService.entity.chapter.chapterVo;
import com.yhh.eduService.service.impl.EduChapterServiceImpl;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-05-23
 */
public interface EduChapterService extends IService<EduChapter> {

    List<chapterVo> getChapterVideoById(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
