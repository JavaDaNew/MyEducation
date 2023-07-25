package com.yhh.eduService.client;

import com.yhh.commonutils.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public Result deleteAlyVideo(String id) {
        return Result.Fail().message("删除视频出错,熔断器...");
    }

    @Override
    public Result deleteBatch(List<String> videoIdList) {
        return Result.Fail().message("删除多个视频出错,熔断器...");
    }
}
