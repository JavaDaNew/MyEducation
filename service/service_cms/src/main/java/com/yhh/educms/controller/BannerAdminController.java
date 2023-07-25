package com.yhh.educms.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yhh.commonutils.Result;
import com.yhh.educms.entity.CrmBanner;
import com.yhh.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 后台管理页面banner表
 * </p>
 *
 * @author testjava
 * @since 2023-06-06
 */
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class BannerAdminController {
    @Autowired
    private CrmBannerService bannerService;
//    1.分页查询
    @GetMapping("pageBanner/{page)/{limit}")
    public Result pageBanner(@PathVariable long page,@PathVariable long limit){
        Page<CrmBanner> bannerPage = new Page<>();
        bannerService.page(bannerPage,null);
        List<CrmBanner> records = bannerPage.getRecords();
        long total = bannerPage.getTotal();
        return Result.OK().data("items",records).data("total",total);
    }

//    2.增加
    @PostMapping("addBanner")
    public Result addBanner(@RequestBody CrmBanner crmBanner){
        bannerService.save(crmBanner);
        return Result.OK();
    }

//    3.修改
//    3.1 根据id查询
    @GetMapping("get/{id}")
    public Result get(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return Result.OK().data("item", banner);
    }
    @PutMapping("updateBanner")
    public Result updateBanner(@RequestBody CrmBanner crmBanner){
        bannerService.updateById(crmBanner);
        return Result.OK();
    }

    //    4.删除
    @DeleteMapping("deleteBanner/{id}")
    public Result deleteBanner(@PathVariable String id){
        bannerService.removeById(id);
        return Result.OK();
    }
}

