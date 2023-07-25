package com.yhh.eduService.entity.subject;

import lombok.Data;
import org.apache.poi.hssf.record.DVALRecord;

import java.util.ArrayList;
import java.util.List;

@Data
public class OneSubject {
    private String id;
    private String title;

    private List<TwoSubject> children = new ArrayList<>();


}
