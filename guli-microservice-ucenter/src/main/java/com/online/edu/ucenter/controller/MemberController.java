package com.online.edu.ucenter.controller;


import com.online.edu.common.R;
import com.online.edu.ucenter.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2020-02-06
 */
@CrossOrigin
@RestController
@RequestMapping("/ucenter/member")
public class MemberController {

    @Autowired
    private IMemberService memberService;

    //统计某一天的注册人数
    @GetMapping("countRegisterNum/{day}")
    public R countRegisterNum(@PathVariable String day){

        Integer result = memberService.countRegisterNum(day);

        return R.ok().data("registerCount",result);
    }


}

