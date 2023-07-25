package com.yhh.commonutils;

import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data; //会生成所有变量的get和set方法

import java.util.HashMap;
import java.util.Map;

@Data
public class Result {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;
    @ApiModelProperty(value = "返回码")
    private Integer code;
    @ApiModelProperty(value = "返回消息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    //把构造方法私有化 ---> 不能new Result这个类
    private Result() {
    }

    ;

    //静态方法
    public static Result OK() {
        Result r = new Result(); //自己可以new这个类 由于构造方法私有化 其他人不能new
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);//->20000
        r.setMessage("成功");
        return r;
    }

    public static Result Fail() {
        Result r = new Result(); //自己可以new这个类 由于构造方法私有化 其他人不能new
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);//->20000
        r.setMessage("失败");
        return r;
    }

    /**
     * this 代表当前对象  谁调用代表谁
     * 链式编程 Result.ok().code().message();
     * @param success
     * @return
     */
    public Result success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public Result message(String message) {
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }

    public Result data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public Result data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }
}
