package com.online.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.online.edu.entity.EduCourse;
import com.online.edu.entity.dto.CourseInfoDto;
import com.online.edu.entity.dto.TeacherAllInfoDto;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2020-02-01
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    //根据课程id查询课程详细信息
    CourseInfoDto getCourseInfoAll(String courseId);

    //前台根据课程id查询课程详情
    TeacherAllInfoDto getTeacherAllInfo(String id);

}
