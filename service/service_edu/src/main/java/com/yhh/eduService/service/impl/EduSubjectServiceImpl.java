package com.yhh.eduService.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yhh.eduService.entity.EduSubject;
import com.yhh.eduService.entity.excel.SubjectData;
import com.yhh.eduService.entity.subject.OneSubject;
import com.yhh.eduService.entity.subject.TwoSubject;
import com.yhh.eduService.listener.SubjectExcelListener;
import com.yhh.eduService.mapper.EduSubjectMapper;
import com.yhh.eduService.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javafx.css.Styleable;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-05-18
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
//            得到文件输入流
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public List<OneSubject> getOneTwoSubject() {
//        1.查询一级  parent_id = 0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);
//        或this.selectList(wrapperOne);

//        2.查询二级  parent_id 不为 0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperOne.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

//        最终集合：存储最终一、二级数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

//        封装一级
        for (int i = 0; i < oneSubjectList.size(); i++) {
            /**
             * 遍历得到每个oneSubject对象，放到oneSubjectList，封装好了以后放到finalSubjectList
             */
            EduSubject eduSubject = oneSubjectList.get(i);
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());
            BeanUtils.copyProperties(eduSubject,oneSubject);// 效果同上

            //把以及分类放入最终集合
            finalSubjectList.add(oneSubject);
//        封装二级
            List<TwoSubject> finalTwoSubjectList = new ArrayList<>();
            for (int m = 0; m < twoSubjectList.size(); m++) {
                EduSubject EduSubject2 = twoSubjectList.get(m);
                if(EduSubject2.getParentId().equals(oneSubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(EduSubject2,twoSubject);
                    finalTwoSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(finalTwoSubjectList);

        }

        return finalSubjectList;

    }
}
