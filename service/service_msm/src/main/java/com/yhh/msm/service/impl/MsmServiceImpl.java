package com.yhh.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.yhh.msm.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
@Service
public class MsmServiceImpl implements MsmService {
    @Override

//    发送短信的方法
    public boolean send(Map<String, Object> param, String phone) {
//        param 包含验证码
        if(StringUtils.isEmpty(phone)){
            return false;
        }
        DefaultProfile profile =
                DefaultProfile.getProfile("cn-hangzhou", "LTAI5tS6D4nXWZ3p3iM3yxQF", "0T7AEQ0D41YGfPBVuFcOPBURCIf1qC");//自己账号的AccessKey信息
        IAcsClient client = new DefaultAcsClient(profile);

//        设置参数
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");//短信服务的服务接入地址
        request.setSysVersion("2017-05-25");//API的版本号
        request.setSysAction("SendSms");//API的名称

        request.putQueryParameter("PhoneNumbers", phone);//接收短信的手机号码
        request.putQueryParameter("SignName", "我的行而上学在线教育平台");//短信签名名称
        request.putQueryParameter("TemplateCode", "SMS_280070274");//短信模板ID
//        map可以直接变成json格式
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));//短信模板变量对应的实际值
        try {
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
