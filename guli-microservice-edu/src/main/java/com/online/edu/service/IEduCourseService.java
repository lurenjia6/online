package com.online.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.entity.EduCourse;
import com.online.edu.entity.dto.CourseInfoDto;
import com.online.edu.entity.dto.TeacherAllInfoDto;
import com.online.edu.entity.form.CourseInfoFrom;
import com.online.edu.entity.query.QueryCourse;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author admin
 * @since 2020-02-01
 */
public interface IEduCourseService extends IService<EduCourse> {


    String insertCourseInfo(CourseInfoFrom courseInfoFrom);

    CourseInfoFrom getIdCourse(String id);

    Boolean updateCourse(CourseInfoFrom courseInfoFrom);

    void pageListCourse(Page<EduCourse> pageCourse, QueryCourse queryCourse);

    boolean removeCourseId(String id);

    CourseInfoDto getCourseInfoAll(String courseId);

    //前台系统课程分页列表
    Map<String, Object> listCoursePage(Page<EduCourse> pageCourse);

    //前台根据课程id查询课程详情
    TeacherAllInfoDto getTeacherAllInfo(String id);
}
