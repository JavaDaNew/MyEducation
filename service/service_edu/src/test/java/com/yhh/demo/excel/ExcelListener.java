package com.yhh.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<ExcelDemo> {
    @Override
//    一行一行读excel内容
    public void invoke(ExcelDemo excelDemo, AnalysisContext analysisContext) {
        System.out.println("listener :" + excelDemo);
    }

//    读取表头内容
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头" + headMap);
    }

    @Override
//    读取完成之后
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
