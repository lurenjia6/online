package com.online.edu.client;

import com.online.edu.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//调用vid服务的接口
@Component
@FeignClient("guli-edu-vod")//指定调用的服务的名字
public interface VidClient {

    //定义调用的方法
    //方法调用路径
    @DeleteMapping("/vidservice/vod/{videoId}")
    public R removeVideoAliyunId(@PathVariable("videoId") String videoId);

    //定义调用删除多个视频的方法
    @DeleteMapping("/vidservice/vod/removeMoreVideo")
    public R removeMoreVideoAlihyun(@RequestParam("videoList") List videoList);

}
