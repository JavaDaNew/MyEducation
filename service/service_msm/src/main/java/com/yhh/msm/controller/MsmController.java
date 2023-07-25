package com.yhh.msm.controller;

import com.yhh.commonutils.Result;
import com.yhh.msm.service.MsmService;
import com.yhh.msm.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {
    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     *  手机号发验证码
     * @param phone
     * @return
     */
//    1.发验证码--验证码值是由代码生成后传给阿里云，阿里云发送到手机，完成传递功能
    @GetMapping("send/{phone}")
    public Result sendMsm(@PathVariable String phone){
//        1.先从redis获取验证码，取到直接返回，取不到进行阿里云发送
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)){
            return Result.OK();
        }
//        2.获取不到，等于空，由代码生成随机值，通过阿里云传递
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
//        调用service中发送短信的方法
        boolean isSend = msmService.send(param,phone);
        if(isSend){
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return Result.OK();
        }else{
            return Result.Fail().message("短信验证码发送失败");
        }

    }
}
