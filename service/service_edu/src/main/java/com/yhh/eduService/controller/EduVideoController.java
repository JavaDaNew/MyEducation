package com.yhh.eduService.controller;


import com.yhh.commonutils.Result;
import com.yhh.eduService.client.VodClient;
import com.yhh.eduService.entity.EduVideo;
import com.yhh.eduService.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-05-23
 */
@RestController
@RequestMapping("/eduService/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;
//    1.添加小节
    @PostMapping("addVideo")
    public Result addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return Result.OK();
    }
/**   2.删除小节及视频
     * 2.1 删除一个视频      -在小节中
     * 2.2删除多个视频  -在章节中
 * **/
//    2.1
    @Autowired
    private VodClient vodClient; //注入删除video的接口
    @DeleteMapping("deleteVideo/{videoId}")
    public Result deleteVideo(@PathVariable String videoId){

        EduVideo video = videoService.getById(videoId);
        String videoSourceId = video.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)){
            vodClient.deleteAlyVideo(videoSourceId);
        }
        videoService.removeById(videoId);
        return Result.OK();
    }

//    3.修改小节
//    3.1 先查出小节信息

    @GetMapping("getVideoInfo/{videoId}")
    public Result getVideoInfo(@PathVariable String videoId){
        EduVideo video = videoService.getById(videoId);
        return Result.OK().data("video",video);
    }
    //    3.2 修改小节信息
    @PostMapping("updateVideo")
    public Result getVideoInfo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return Result.OK();
    }



}

