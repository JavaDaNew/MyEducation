package com.yhh.educenter.controller;


import com.yhh.commonutils.JwtUtils;
import com.yhh.commonutils.Result;
import com.yhh.commonutils.orderVo.UcenterMemberOrder;
import com.yhh.educenter.entity.UcenterMember;
import com.yhh.educenter.entity.vo.RegisterVo;
import com.yhh.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * 登录 注册
 * </p>
 *
 * @author testjava
 * @since 2023-06-13
 */

@RestController
@RequestMapping("/ucenterservice/member")
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;

//    1.登录方法
    @PostMapping("login")
    public Result login(@RequestBody UcenterMember member){
//        调用service方法实现登录
//        返回token值 使用jwt生成
        String token = memberService.login(member);
        return Result.OK().data("token",token);
    }

//    2.注册登录
    @PostMapping("register")
    public Result registerUser(@RequestBody RegisterVo registerVo){
//        调用service方法实现登录
//        返回token值 使用jwt生成
        memberService.register(registerVo);
        return Result.OK();
    }

//    3.根据token获取用户信息 在前端进行显示
    @GetMapping("getMemberInfo")
    public Result getMemberInfo(HttpServletRequest request){
//        调用jwt方法 根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
//        根据id查询数据库，获取用户信息
        UcenterMember member = memberService.getById(memberId);
        return Result.OK().data("userInfo",member);
    }

    //    4.根据用户id获取用户信息，便于远程调用—— 在orderService中
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id){
        UcenterMember member = memberService.getById(id);
//        member中的值复制给member
        UcenterMemberOrder ucenterMember = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMember);
        return ucenterMember;
    }

/**
 * 5.查询某天的注册人数
 *     通过编写sql语句实现
 *     select count(*) from ucenter_members where date = ’2023-4-5‘
  */


    @GetMapping("countRegister/{day}")
    public Result countRegister(@PathVariable String day){
       Integer count = memberService.countRegisterDay(day);
        return Result.OK().data("countRegister",count);
    }

}

