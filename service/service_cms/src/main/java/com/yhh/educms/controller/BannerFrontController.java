package com.yhh.educms.controller;


import com.yhh.commonutils.Result;
import com.yhh.educms.entity.CrmBanner;
import com.yhh.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-06-06
 */
@RestController
@RequestMapping("/educms/bannerfront")
@CrossOrigin
public class BannerFrontController {
    
    @Autowired
    private CrmBannerService bannerService;

//    1.查询所有的banner
    @GetMapping("getAllBanner")
    public Result getAllBanner(){
//        为了后面加redis方便  另写了一个方法
        List<CrmBanner> list = bannerService.selectAllBanner();
        return Result.OK().data("list",list);
    }
}

