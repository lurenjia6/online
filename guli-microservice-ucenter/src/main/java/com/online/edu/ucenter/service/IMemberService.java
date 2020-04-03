package com.online.edu.ucenter.service;

import com.online.edu.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author admin
 * @since 2020-02-06
 */
public interface IMemberService extends IService<Member> {

    //统计某一天的注册人数
    Integer countRegisterNum(String day);
}
