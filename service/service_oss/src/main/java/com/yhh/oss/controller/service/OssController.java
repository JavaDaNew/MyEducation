package com.yhh.oss.controller.service;


import com.yhh.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduOss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping
    public Result uploadOssFile(MultipartFile file){
        //获取上传文件,返回上传到oss的路径
        String url = ossService.uploadFileAvatar(file);
        return Result.OK().data("url",url);
    }

}
