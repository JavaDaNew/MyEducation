package com.yhh.educenter.controller;

import com.google.gson.Gson;
import com.yhh.commonutils.JwtUtils;
import com.yhh.educenter.entity.UcenterMember;
import com.yhh.educenter.service.UcenterMemberService;
import com.yhh.educenter.utils.ConstantWxUtils;
import com.yhh.educenter.utils.HttpClientUtils;
import com.yhh.servicebase.exceptionhandler.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.util.HashMap;

@CrossOrigin
@Controller //restController 返回数据 现在不需要返回数据
@RequestMapping("/api/ucenter/wx")
public class WxApiControlller {
    @Autowired
    private UcenterMemberService memberService;
//    1.生成微信扫描二维码
    @GetMapping("login")
    public String getWxCode() {
        //固定地址，后面拼接参数
//        String url = "https://open.weixin.qq.com/" +
//                "connect/qrconnect?appid="+ ConstantWxUtils.WX_OPEN_APP_ID+"&response_type=code";

        // 微信开放平台授权baseUrl  %s相当于?代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //对redirect_url进行URLEncoder编码
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        }catch(Exception e) {
        }

        //设置%s里面值
        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "yhh"
        );
        //重定向到请求微信地址里面
        return "redirect:"+url;
    }

    /**   为了测试用
     *   2 .扫完二维码后，调用本地的方法进行操作
     * //        获取信息  或添加数据
     */

    @GetMapping("callback")
    public String callback(String code,String state){
        try {
            //        2.1 获取code值 作为临时票据
//        2.2 通过code请求微信固定地址 得到access_token和 openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //拼接三个参数 ：id  秘钥 和 code值
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code
            );
            //请求这个拼接好的地址，得到返回两个值 accsess_token 和 openid
            //使用httpclient发送请求，得到返回结果
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            //从accessTokenInfo字符串获取出来两个值 accsess_token 和 openid
            //把accessTokenInfo字符串转换map集合，根据map里面key获取对应值
            //使用json转换工具 Gson
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String accessToken = (String)mapAccessToken.get("access_token");
            String openid = (String)mapAccessToken.get("openid");


//            判断DB是否存在相同用户信息  openid唯一  ---》查询 ：注入service
            UcenterMember member = memberService.getOpenIdMember(openid);
            if (member == null){
                //3 拿着得到accsess_token 和 openid，再去请求微信提供固定的地址，获取到扫描人信息
                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                //拼接两个参数
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        accessToken,
                        openid
                );

                String userInfo = HttpClientUtils.get(userInfoUrl);
                //获取返回userinfo字符串扫描人信息
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String)userInfoMap.get("nickname");
                String headimgurl = (String)userInfoMap.get("headimgurl");//头像

                //添加
                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                memberService.save(member);

            }
//            使用JWT 根据member生成token字符串   因为cookie不能跨域
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            StringBuilder sb = new StringBuilder();
            StringBuffer buffer = new StringBuffer();
            String s = " abcd   ";
            sb.append(12);
            sb.append("%20");
            sb.append(1.26);
            sb.length();
            char[] chars = s.toCharArray();
            chars[2] = '%';
            String s1 = new String(chars);
//        回到主页面 显示用户昵称和头像
        return "redirect:http://localhost:3000?Token="+jwtToken;
        }catch (Exception e){
            throw new MyException(20001,"微信登录失败！");
        }




    }
}
