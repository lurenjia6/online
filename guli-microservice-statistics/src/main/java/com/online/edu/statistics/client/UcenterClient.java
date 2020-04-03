package com.online.edu.statistics.client;

import com.online.edu.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//调用vid服务的接口
@Component
@FeignClient("guli-edu-ucenter")//指定调用的服务的名字
public interface UcenterClient {


    //得到某一天注册人数的方法定义
    @GetMapping("/ucenter/member/countRegisterNum/{day}")
    public R countRegisterNum(@PathVariable("day") String day);
}
