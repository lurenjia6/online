package com.online.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.entity.EduCourse;
import com.online.edu.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.entity.query.QueryTeacher;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类，服务层接口
 * </p>
 *
 * @author admin
 * @since 2020-01-02
 */
public interface IEduTeacherService extends IService<EduTeacher> {
    //条件查询带分页
    void pageListCondition(Page<EduTeacher> pageTeacher, QueryTeacher queryTeacher);

    //前台分页查询讲师的方法
    Map<String, Object> getFrontTeacherList(Page<EduTeacher> pageTeacher);

    //查询讲师详情信息
    List<EduCourse> getCourseListByTeacherId(String id);
}
