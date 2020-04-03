package com.online.edu.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.edu.entity.EduCourse;
import com.online.edu.entity.EduCourseDescription;
import com.online.edu.entity.dto.CourseInfoDto;
import com.online.edu.entity.dto.TeacherAllInfoDto;
import com.online.edu.entity.form.CourseInfoFrom;
import com.online.edu.entity.query.QueryCourse;
import com.online.edu.handler.EduException;
import com.online.edu.mapper.EduCourseMapper;
import com.online.edu.service.IEduChapterService;
import com.online.edu.service.IEduCourseDescriptionService;
import com.online.edu.service.IEduCourseService;
import com.online.edu.service.IEduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-02-01
 */
@Service
@Transactional
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements IEduCourseService {

    //自动注入课程描述service层
    @Autowired
    private IEduCourseDescriptionService courseDescriptionService;

    //课程章节
    @Autowired
    private IEduChapterService eduChapterService;

    //课程小节
    @Autowired
    private IEduVideoService eduVideoService;

    //1.添加课程信息
    @Override
    public String insertCourseInfo(CourseInfoFrom courseInfoFrom) {
        //1.课程的基本信息添加到课程表中
        //courseInfoFrom数据复制到Course对象里面，进行添加
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoFrom, course);
        int result = baseMapper.insert(course);

        //判断添加课程信息成功，就添加描述
        if (result == 0) {
            //抛出异常
            throw new EduException(20001, "添加课程信息失败");
        }

        //2.课程描述添加到课程描述表中
        EduCourseDescription courseDescription = new EduCourseDescription();

        //获取描述信息
        String description = courseInfoFrom.getDescription();
        courseDescription.setDescription(description);

        //课程id
        String courseId = course.getId();
        courseDescription.setId(courseId);
        boolean save = courseDescriptionService.save(courseDescription);

        if (save) {
            return courseId;
        } else {
            return null;
        }
    }

    //根据id查询课程信息
    @Override
    public CourseInfoFrom getIdCourse(String id) {
        //查询两张表

        //1.根据id查询课程基本信息表
        EduCourse eduCourse = baseMapper.selectById(id);
        if (eduCourse == null) {
            //没有课程信息
            throw new EduException(20001, "没有课程信息");
        }

        CourseInfoFrom courseInfoFrom = new CourseInfoFrom();
        BeanUtils.copyProperties(eduCourse, courseInfoFrom);//复制对象

        //到上一行代码时候，courseInfoFrom对象里面已经有课程基本信息，没有描述信息


        //2.根据id查询课程描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(id);
        String description = courseDescription.getDescription();
        courseInfoFrom.setDescription(description);

        //返回封装之后的对象
        return courseInfoFrom;
    }

    //修改课程信息
    @Override
    public Boolean updateCourse(CourseInfoFrom courseInfoFrom) {

        //1.修改课程基本信息表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoFrom, eduCourse);
        int result = baseMapper.updateById(eduCourse);
        if (result == 0) {
            throw new EduException(20001, "修改分类失败");
        }


        //2.修改描述表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        String id = courseInfoFrom.getId();
        String description = courseInfoFrom.getDescription();

        eduCourseDescription.setId(id);
        eduCourseDescription.setDescription(description);

        boolean b = courseDescriptionService.updateById(eduCourseDescription);

        return b;
    }

    /**
     * 课程列表条件查询带分页
     * 1.先判断封装条件对象值是不是为空，
     * 1.1为空就直接查询分页,不进行条件操作
     * 2.如果封装条件对象不为空,从封装条件对象对象里面获取条件值
     * 3.判断条件值是否有,如果有,就拼接条件
     * 4.最后调用baseMapper.selectPage(pageCourse, wrapper)，实现分页查询
     *
     * @param pageCourse  分页对象
     * @param queryCourse 用于封装条件值
     */
    @Override
    public void pageListCourse(Page<EduCourse> pageCourse, QueryCourse queryCourse) {

        //关键:queryTeacher有传递过来的条件值,判断,如果有条件值,拼接条件
        if (queryCourse == null) {//对象为空
            //直接查询分页,不进行条件操作
            baseMapper.selectPage(pageCourse, null);
            return;
        }

        //如果queryCourse不为空,从queryCourse对象里面获取条件值
        String title = queryCourse.getTitle();
        BigDecimal price = queryCourse.getPrice();
        String begin = queryCourse.getBegin();
        String end = queryCourse.getEnd();

        //判断条件值是否有,如果有,就拼接条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(title)) {
            //拼接条件
            wrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(price)) {
            //拼接条件
            wrapper.eq("price", price);
        }

        if (!StringUtils.isEmpty(begin)) {
            //拼接条件
            wrapper.ge("gmt_create", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            //拼接条件
            wrapper.le("gmt_create", end);
        }

        //条件查询带分页
        baseMapper.selectPage(pageCourse, wrapper);
    }

    //根据课程的id删除课程
    @Override
    public boolean removeCourseId(String id) {
        //1.根据课程id删除章节
        eduChapterService.deleteChapterByCourseId(id);

        //2.根据课程id删除小节
        eduVideoService.deleteVideoByCourseId(id);

        //3.根据课程id删除课程描述
        courseDescriptionService.deleteDescriptionByCourseId(id);

        //4.删除课程本身
        int result = baseMapper.deleteById(id);


        return result > 0;
    }

    //根据课程id查询课程详细信息
    @Override
    public CourseInfoDto getCourseInfoAll(String courseId) {

        CourseInfoDto courseInfoAll = baseMapper.getCourseInfoAll(courseId);

        return courseInfoAll;
    }

    //前台系统课程分页列表
    @Override
    public Map<String, Object> listCoursePage(Page<EduCourse> pageCourse) {
        baseMapper.selectPage(pageCourse,null);

        List<EduCourse> records = pageCourse.getRecords();//每页数据
        long total = pageCourse.getTotal();//总记录数
        long size = pageCourse.getSize();//每页显示记录数
        long pages = pageCourse.getPages();//总页数
        long current = pageCourse.getCurrent();//当前页
        boolean hasNext = pageCourse.hasNext();//是否有下一页
        boolean hasPrevious = pageCourse.hasPrevious();//是否有上一页

        //把分页数据放到map集合里面
        Map<String,Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    //前台根据课程id查询课程详情
    @Override
    public TeacherAllInfoDto getTeacherAllInfo(String id) {
        return baseMapper.getTeacherAllInfo(id);
    }
}
