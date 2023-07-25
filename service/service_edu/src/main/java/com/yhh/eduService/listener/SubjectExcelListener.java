package com.yhh.eduService.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yhh.eduService.entity.EduSubject;
import com.yhh.eduService.entity.excel.SubjectData;
import com.yhh.eduService.service.EduSubjectService;
import com.yhh.servicebase.exceptionhandler.GlobalExceptionHandler;
import com.yhh.servicebase.exceptionhandler.MyException;
import org.apache.poi.hssf.record.DVALRecord;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    /**
     * 因为SubjectExcelListener不能交给spring管理，需要自己new，不能注入其他对象，不方便实现数据库操作
     * 解决：手动注入service
     * @param subjectData
     * @param analysisContext
     */
    public EduSubjectService subjectService;

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }
    public SubjectExcelListener() {}

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null){
            throw new MyException(20001,"文件数据为空");
        }

        EduSubject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if (existOneSubject == null ){
//            没有相同的一级分类 -》进行添加
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(existOneSubject);
        }
//        获取一级分类值
        String pid = existOneSubject.getId();
        EduSubject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if(existTwoSubject == null){
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(existTwoSubject);
        }

    }

//    判断一级分类不能重复添加 EduSubjectService 为了便于查询
    private EduSubject existOneSubject(EduSubjectService subjectService,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }
//    判断二级分类不能重复添加
private EduSubject existTwoSubject(EduSubjectService subjectService,String name,String pid){
    QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
    wrapper.eq("title",name);
    wrapper.eq("parent_id",pid);
    EduSubject twoSubject = subjectService.getOne(wrapper);
    return twoSubject;
}
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
