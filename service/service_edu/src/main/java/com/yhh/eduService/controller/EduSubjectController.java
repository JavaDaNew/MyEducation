package com.yhh.eduService.controller;


import com.yhh.commonutils.Result;
import com.yhh.eduService.entity.subject.OneSubject;
import com.yhh.eduService.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-05-18
 */
@RestController
@RequestMapping("/eduService/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;

//    添加课程分类
//    获取上传文件，读取文件内容
    @PostMapping("addSubject")
    public Result addSubject(MultipartFile file){
//        得到上传的excel文件
        subjectService.saveSubject(file,subjectService);
        return Result.OK();
    }

//    课程分类列表-树型结构
    @GetMapping("getAllSubject")
    public Result getAllSubject(){
        List<OneSubject> list = subjectService.getOneTwoSubject();
        return Result.OK().data("list",list);
    }
}


