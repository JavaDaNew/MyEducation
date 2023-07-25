package com.yhh.educenter.service;

import com.yhh.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yhh.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-06-13
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid);

    Integer countRegisterDay(String day);
}
