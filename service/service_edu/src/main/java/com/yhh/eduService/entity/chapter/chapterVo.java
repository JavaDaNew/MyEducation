package com.yhh.eduService.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class chapterVo {
    private String title;
    private String id;

//    表示小节 1章节--多小节
    private List<videoVo> children = new ArrayList<>();
}
