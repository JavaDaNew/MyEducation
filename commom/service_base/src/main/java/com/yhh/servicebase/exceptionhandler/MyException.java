package com.yhh.servicebase.exceptionhandler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  //lombok 生成get set方法
@AllArgsConstructor  //生成有参数构造
@NoArgsConstructor  //无参构造
public class MyException extends RuntimeException{

    @ApiModelProperty(value = "状态码")
    private Integer code;
    private String msg;

}
