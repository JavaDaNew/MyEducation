package com.yhh.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yhh.eduService.entity.EduTeacher;
import com.yhh.eduService.mapper.EduTeacherMapper;
import com.yhh.eduService.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-04-10
 */
//service 也做了封装 包括（创建sql等
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
//    分页查询讲师
    public Map<String, Object> getTeacherFront(Page<EduTeacher> teacherPage) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("id");
//        分页好的数据封装到teacherPage
        baseMapper.selectPage(teacherPage,wrapper);
        List<EduTeacher> records = teacherPage.getRecords();
        long current = teacherPage.getCurrent();
        long total = teacherPage.getTotal();
        long pages = teacherPage.getPages();
        long size = teacherPage.getSize();
        boolean next = teacherPage.hasNext(); //下一页
        boolean previous = teacherPage.hasPrevious(); //上一页
        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("Next", next);
        map.put("Previous", previous);
        return map;
    }
}
