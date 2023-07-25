package com.yhh.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.yhh.commonutils.Result;
import com.yhh.servicebase.exceptionhandler.MyException;
import com.yhh.vod.Utils.ConstantVodUtils;
import com.yhh.vod.Utils.InitVodClient;
import com.yhh.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;
//    1.上传视频到阿里云
    @PostMapping("uploadAlyVideo")
    public Result uploadAlyVideo(MultipartFile file){
        String videoId = vodService.uploadVideoAly(file);
        return Result.OK().data("videoId",videoId);
    }

//    2.根据视频id删除阿里云中的视频
    @DeleteMapping("deleteAlyVideo/{id}")
    public Result deleteAlyVideo(@PathVariable String id){
        try{

//            初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
//        创建一个删除视频的对象
            DeleteVideoRequest request = new DeleteVideoRequest();
//            向request中设置视频id
            request.setVideoIds(id);
//            调用初始化对象的方法实现删除
            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
            throw new MyException(20001,"删除阿里云视频失败");
        }
        return Result.OK();
    }

//    3.删除多个视频
    @DeleteMapping("delete-batch")
    public Result deleteBatch(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.removeMoreAlyViedo(videoIdList);
        return Result.OK();
    }

//    4.根据视频id获取视频的播放凭证
    @GetMapping("getPlayAuth/{id}")
    public Result getPlayAuth(@PathVariable String id){
        try {
//            创建初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return Result.OK().data("playAuth",playAuth);
        }catch (Exception e){
            throw new MyException(20001,"获取视频播放凭证失败");
        }



    }


}
