package com.yhh.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yhh.commonutils.JwtUtils;
import com.yhh.commonutils.MD5;
import com.yhh.educenter.entity.UcenterMember;
import com.yhh.educenter.entity.vo.RegisterVo;
import com.yhh.educenter.mapper.UcenterMemberMapper;
import com.yhh.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yhh.servicebase.exceptionhandler.MyException;
import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.QuerydslRepositoryInvokerAdapter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-06-13
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
//    1.登录
    @Override
    public String login(UcenterMember member) {
//        获取登录手机号 密码
        String mobile = member.getMobile();
        String password = member.getPassword();

//        为空 不执行
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new MyException(20001,"登陆失败");
        }
//        判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
//        判断手机号查出的对象是否为空
        if (mobileMember == null){
            throw new MyException(20001,"手机号不存在");
        }
//        判断密码是否正确  -先加密 再和数据库中的对比
//        加密方式---MD5加密 ：只能加密 不能解密
        if(!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new MyException(20001,"密码错误");
        }
        //        用用户是否被禁用
        if(mobileMember.getIsDisabled()){
            throw new MyException(20001,"该用户已被禁用！");
        }

//        登陆成功 --》 生成token字符串
        String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());

        return jwtToken;
    }
//  2.注册
    @Override
    public void register(RegisterVo registerVo) {
//        获取注册数据
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
//        非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(code)
                || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)) {
            throw new MyException(20001, "传入参数不能为空!");
        }
//  获取redis中的验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)){
            throw new MyException(20001, "验证码输入错误！");
        }
//        手机号不能重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0){
            throw new MyException(20001, "该手机号已存在！");
        }

//        添加用户到数据库
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("https://guli-file-190513.oss-cn-beijing.aliyuncs.com/avatar/default.jpg");
        baseMapper.insert(member);

    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
