package com.yhh.eduService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yhh.commonutils.Result;
import com.yhh.eduService.entity.EduTeacher;
import com.yhh.eduService.entity.vo.TeacherQuery;
import com.yhh.eduService.service.EduTeacherService;
import com.yhh.servicebase.exceptionhandler.MyException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-04-10
 */
@Api("讲师管理")
@RestController
//@Controller 交给spring管理数据 @ResponseBody 返回json数据
@RequestMapping("/eduService/teacher") //随便起名即可
@CrossOrigin
/**
 * 最终访问地址 http://localhost:8001/eduService/teacher/findAll
 */
public class EduTeacherController {
    //controller去调用service中的方法 ——> 注入service
    @Autowired
    private EduTeacherService edu;

    //1.查询讲师表的所有数据
    //rest风格 用不同的方式提交
    @ApiOperation("1.查询所有讲师列表")
    @GetMapping("findAll") //findAll可以不加
    public Result findAllTeacher(){
    //调用service的方法实现查询所有
        //之前增删改查的操作是在service中写  @Select(select * from edu_teacher)
        List<EduTeacher> list = edu.list(null);
        //wrapper 中放入查询条件
        return Result.OK().data("items",list);
    }

    //2. 逻辑删除讲师
    @ApiOperation("2.逻辑删除讲师")
    @DeleteMapping("{id}")
    //通过路径传id值 http://localhost:8001/eduService/teacher/id
    public Result removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id){
        boolean flag = edu.removeById(id);
        if (flag){
            return Result.OK();
        }else {
            return Result.Fail();
        }
    }

    //3.分页查询讲师
    @ApiOperation("3.分页查询讲师")
    @GetMapping("pageTeacher/{curr}/{limit}")
    public Result PageListT(@PathVariable long curr,
                            @PathVariable long limit){
        //3.1 创建page对象 当前页curr 每页记录数limit
        Page<EduTeacher> teacherPage = new Page<>(curr,limit);
        //3.2 service已经注入 用service来调用
        //调用方法时 底层会把数据封装到teacherPage中
        edu.page(teacherPage, null);
        long total = teacherPage.getTotal(); //总记录数
//        try {
////            int x = 5 / 0;
////        }catch (Exception e){
////            //执行自定义异常 手动抛出
////            throw new MyException(20001,"执行了自定义异常处理...");
////        }


        List<EduTeacher> records = teacherPage.getRecords(); //每一页数据list集合
        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);
        return Result.OK().data(map);
    }


    //4. 条件查询带分页
    @ApiOperation("4.条件查询带分页")
    @PostMapping("PageTeacherCondition/{cur}/{limit}")
    public Result PageTeacherCondition(@PathVariable long cur,
                                       @PathVariable long limit,
                                       @RequestBody(required = false) TeacherQuery teacherQuery
                                       )
    /**
     *  @RequestBody -> 使用json传递数据时，会把数据封装到对象里面 （网页提交 传给后端）
     *  使用POST提交
     *  required = false -> 参数值可以为空
     *  @ResponseBody -> 返回json数据
     */
    {
        // 查询条件用QuerryWrapper对象 调用里面的方法即可
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //方法如ge le eq ne between like 等
        // 动态sql
        //判断条件是否为空 如果不为空则拼接条件
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            //构建条件
            wrapper.like("name",name);//表里字段名 传入的值
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_modified",end);
        }

        //按照时间排序
//        wrapper.orderByAsc("gmt_create");
        //调用方法获得分页数据
        Page<EduTeacher> pageParam = new Page<>(cur,limit);
        edu.page(pageParam,wrapper);
        long total = pageParam.getTotal();
        List<EduTeacher> records = pageParam.getRecords();
//      传递数据 用统一结果返回
//        HashMap<Object, Object> map = new HashMap<>();
//        map.put("total",total);
//        map.put("rows",records);
//        return Result.OK().data(map);
        return Result.OK().data("total",total).data("rows",records);
    }

    //5. 添加讲师
    @ApiOperation("5.添加讲师")
    @PostMapping("addTeacher")
    public Result addTeacher(@RequestBody EduTeacher eduTeacher){
        System.out.println(eduTeacher);
        boolean newTeacher = edu.save(eduTeacher);
        System.out.println(newTeacher);
        return newTeacher ? Result.OK() : Result.Fail();
//        if (newTeacher){
//            return Result.OK();
//        }else{
//            return Result.Fail();
//        }
    }

    //6. 修改讲师信息
    //6.1 根据id查询讲师信息
    @ApiOperation("6.根据id查询讲师信息")
    @GetMapping("getTeacher/{id}")
    public Result getTeacher(@PathVariable String id){
        EduTeacher eduById = edu.getById(id);
        return Result.OK().data("items",eduById);
    }
    //6.2 讲师信息修改
    @ApiOperation("7.修改讲师信息")
    @PostMapping("updateTeacher")  //用PutMapping也可以
    public Result updateTeacher(@RequestBody EduTeacher eduTeacher){

        boolean flag = edu.updateById(eduTeacher);

        return flag ? Result.OK() : Result.Fail();
    }




}

