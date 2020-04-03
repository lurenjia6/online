package com.online.edu.service;

import com.online.edu.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author admin
 * @since 2020-02-03
 */
public interface IEduVideoService extends IService<EduVideo> {

    //根据课程id删除小节
    void deleteVideoByCourseId(String id);

    //删除小节
    boolean removeVideo(String videoId);
}
