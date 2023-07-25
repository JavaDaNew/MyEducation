package com.yhh.servicebase.exceptionhandler;

import com.yhh.commonutils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j  //使用日志
public class GlobalExceptionHandler {  //统一异常处理
    /**
     * 1.全局异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody //为了返回数据
    public Result error(Exception e){
        e.printStackTrace();
        return Result.Fail().message("（全局）系统繁忙，请稍后再试...");
    }
    /**
     * Result 统一返回结果报错 --> 在pom文件中引入common_utils的包
     */

    /**
     * 2.特殊异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody //为了返回数据
    public Result error(ArithmeticException e){
        e.printStackTrace();
        return Result.Fail().message("（特殊）ArithmeticException处理...\"");

    }

    /**
     * 3.自定义异常处理 -> 自定义异常类
     * @param e
     * @return
     */
    @ExceptionHandler(MyException.class)
    @ResponseBody //为了返回数据
    public Result error(MyException e){
        log.error(e.getMsg());  //信息写到日志里面去

        e.printStackTrace();
        return Result.Fail().code(e.getCode()).message(e.getMsg());

    }

}
