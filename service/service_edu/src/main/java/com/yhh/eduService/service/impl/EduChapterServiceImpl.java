package com.yhh.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yhh.eduService.entity.EduChapter;
import com.yhh.eduService.entity.EduVideo;
import com.yhh.eduService.entity.chapter.chapterVo;
import com.yhh.eduService.entity.chapter.videoVo;
import com.yhh.eduService.mapper.EduChapterMapper;
import com.yhh.eduService.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhh.eduService.service.EduVideoService;
import com.yhh.servicebase.exceptionhandler.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 *
 *
 * @since 2023-05-23
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService videoService;
//    课程大纲列表，根据课程id查询
    @Override
    public List<chapterVo> getChapterVideoById(String courseId) {
//        1.根据课程id得到所有章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);
//        2.得到章节中的所有小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);
//        3.遍历章节list，封装
        List<chapterVo> finalList = new ArrayList<>();
//        4.遍历小节list，封装
        for (int i = 0; i < eduChapterList.size(); i++) {
            EduChapter eduChapter = eduChapterList.get(i);
            chapterVo chapterVo = new chapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            finalList.add(chapterVo);
//            课程小节
            List<videoVo> children = new ArrayList<>();
            for (int m = 0; m < eduVideoList.size(); m++) {
                EduVideo eduVideo = eduVideoList.get(m);
                if(eduVideo.getChapterId().equals(chapterVo.getId())){
                    videoVo videoVo = new videoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    children.add(videoVo);
                }
            }
        chapterVo.setChildren(children);
        }
        return finalList;
    }
// 删除章节的方法
    @Override
    public boolean deleteChapter(String chapterId) {
//        根据章节id查询小节表-》有数据，不删
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        /**
         * 根据条件能查出几条记录
         */
        if (count > 0){
            throw new MyException(20001,"不能删除有小节的章节！");
        }else {
//            删除章节
            int result = baseMapper.deleteById(chapterId);
            return result > 0;
        }


    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
