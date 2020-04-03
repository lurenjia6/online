package com.online.edu.service;

import com.online.edu.entity.EduCourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author admin
 * @since 2020-02-01
 */
public interface IEduCourseDescriptionService extends IService<EduCourseDescription> {

    //根据课程id删除课程描述
    void deleteDescriptionByCourseId(String id);
}
