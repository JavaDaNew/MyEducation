package com.yhh.eduService.service;

import com.yhh.eduService.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-05-23
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeVideoByCourseId(String id);
}
