package com.yhh.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExcelDemo {
    @ExcelProperty(value = "学生编号",index = 0)
    private Integer snum;
    @ExcelProperty(value = "学生姓名",index = 1)
    private String  sname;
}
