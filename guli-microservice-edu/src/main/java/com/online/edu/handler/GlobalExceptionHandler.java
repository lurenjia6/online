package com.online.edu.handler;

import com.online.edu.common.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *统一异常处理类
 * 1.写个类,在类上添加注解@ControllerAdvice,SpringAOP思想
 * 2.写个方法,在方法上添加@ResponseBody,@ExceptionHandler(Exception.class)注解
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    //1.对所有异常都进行相同的处理
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("出现了异常");
    }

    //2.对特定的异常进行处理
    @ResponseBody
        @ExceptionHandler(RuntimeException.class)
    public R error(RuntimeException e){
        e.printStackTrace();
        return R.error().message("出现了运行时异常");
    }

    //3.对特定的异常进行处理
    @ResponseBody
    @ExceptionHandler(EduException.class)
    public R error(EduException e){
        e.printStackTrace();
        return R.error().message(e.getMessage()).code(e.getCode());
    }
}
