package com.online.edu.controller;


import com.online.edu.common.R;
import com.online.edu.entity.EduVideo;
import com.online.edu.service.IEduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author admin
 * @since 2020-02-03
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired
    private IEduVideoService eduVideoService;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        boolean save = eduVideoService.save(eduVideo);

        if(save){
            return R.ok();
        }else {
            return R.error();
        }

    }

    //根据id查询
    @GetMapping("{videoId}")
    public R getVideoId(@PathVariable String videoId){
        EduVideo eduVideo = eduVideoService.getById(videoId);

        return R.ok().data("eduVideo",eduVideo);
    }

    //修改的方法
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        boolean result = eduVideoService.updateById(eduVideo);

        if(result){
            return R.ok();
        }else {
            return R.error();
        }

    }

    //删除的方法
    @DeleteMapping("{videoId}")
    public R deleteVideoId(@PathVariable String videoId){
        boolean flag = eduVideoService.removeVideo(videoId);

        if(flag){
            return R.ok();
        }else {
            return R.error();
        }

    }

}

