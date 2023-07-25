package com.yhh.educms.service;

import com.yhh.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-06-06
 */
public interface CrmBannerService extends IService<CrmBanner> {

//    查询所有的banner
    List<CrmBanner> selectAllBanner();
}
