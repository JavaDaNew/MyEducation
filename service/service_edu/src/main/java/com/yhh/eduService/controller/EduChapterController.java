package com.yhh.eduService.controller;


import com.yhh.commonutils.Result;
import com.yhh.eduService.entity.EduChapter;
import com.yhh.eduService.entity.chapter.chapterVo;
import com.yhh.eduService.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-05-23
 */
@RestController
@RequestMapping("/eduService/chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;
//    1.返回大纲列表
    @GetMapping("getChapterVo/{courseId}")
    public Result getChapterVo (@PathVariable String courseId){
        List<chapterVo> list = chapterService.getChapterVideoById(courseId);

        return Result.OK().data("allChapter",list);
    }

//    添加章节
    @PostMapping("addChapter")
    public Result addChapter(@RequestBody EduChapter eduChapter){
        chapterService.save(eduChapter);
        return Result.OK();
    }

//    修改章节——》先查后显
    @GetMapping("getChapterInfo/{chapterId}")
    public Result getChapterInfo(@PathVariable String chapterId){
        EduChapter eduChapter = chapterService.getById(chapterId);
        return Result.OK().data("chapter",eduChapter);
    }


    @PostMapping("updateChapter")
    public Result updateChapter(@RequestBody EduChapter eduChapter){
        chapterService.updateById(eduChapter);
        return Result.OK();
    }

    @DeleteMapping("deleteChapter/{chapterId}")
    public Result deleteChapter(@PathVariable String chapterId){
        boolean flag = chapterService.deleteChapter(chapterId);
        if (flag){
            return Result.OK();
        }else {
            return Result.Fail();
        }

    }
}

