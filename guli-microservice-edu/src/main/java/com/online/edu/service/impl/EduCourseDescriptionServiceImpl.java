package com.online.edu.service.impl;

import com.online.edu.entity.EduCourseDescription;
import com.online.edu.mapper.EduCourseDescriptionMapper;
import com.online.edu.service.IEduCourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-02-01
 */
@Service
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements IEduCourseDescriptionService {

    //根据课程id删除课程描述
    @Override
    public void deleteDescriptionByCourseId(String id) {
        baseMapper.deleteById(id);
    }
}
