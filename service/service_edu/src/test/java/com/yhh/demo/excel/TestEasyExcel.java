package com.yhh.demo.excel;

import com.alibaba.excel.EasyExcel;


import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
        /**
         * 实现easyExcel写操作
         */
//        设置写入文件夹地址和excel名称
//        String filename = "E:\\Java.xlsx";
//        EasyExcel.write(filename,ExcelDemo.class).sheet("学生列表").doWrite(getList());

        /**
         * 实现easyExcel读操作
         */
        String filename = "E:\\Java.xlsx";
        EasyExcel.read(filename,ExcelDemo.class,new ExcelListener()).sheet().doRead();

    }

    private static List<ExcelDemo> getList(){
        List<ExcelDemo> date = new ArrayList<>();
        for (int i = 0; i < 10 ; i++) {
            ExcelDemo excelDemo = new ExcelDemo();

            excelDemo.setSnum(i);
            excelDemo.setSname("Nineno"+i);

            date.add(excelDemo);
        }
        return date;
    }
}
