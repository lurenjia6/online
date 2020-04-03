package com.online.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.edu.entity.EduCourse;
import com.online.edu.entity.EduTeacher;
import com.online.edu.entity.query.QueryTeacher;
import com.online.edu.mapper.EduTeacherMapper;
import com.online.edu.service.IEduCourseService;
import com.online.edu.service.IEduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-01-02
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements IEduTeacherService {


    @Autowired
    private IEduCourseService eduCourseService;

    /**
     * 讲师列表条件查询带分页
     *
     * @param pageTeacher  分页对象
     * @param queryTeacher 用于封装条件值
     */
    @Override
    public void pageListCondition(Page<EduTeacher> pageTeacher, QueryTeacher queryTeacher) {
        // int i = 3/0;
        //关键:queryTeacher有传递过来的条件值,判断,如果有条件值,拼接条件
        if (queryTeacher == null) {//对象为空
            //直接查询分页,不进行条件操作
            baseMapper.selectPage(pageTeacher, null);
            return;
        }

        //如果queryTeacher不为空,从queryTeacher对象里面获取条件值
        String name = queryTeacher.getName();
        String level = queryTeacher.getLevel();
        String begin = queryTeacher.getBegin();
        String end = queryTeacher.getEnd();

        //判断条件值是否有,如果有,就拼接条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            //拼接条件
            wrapper.like("name", name);
        }

        if (!StringUtils.isEmpty(level)) {
            //拼接条件
            wrapper.eq("level", level);
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
        //baseMapper.selectPage(pageTeacher, wrapper);
        baseMapper.selectPage(pageTeacher,wrapper);
    }

    //前台分页查询讲师的方法
    @Override
    public Map<String, Object> getFrontTeacherList(Page<EduTeacher> pageTeacher) {

        //调用方法进行分页查询，通过pageTeacher对象获取分页之后的数据
        baseMapper.selectPage(pageTeacher,null);

        //从pageTeacher分页数据获取出来，放到map集合
        List<EduTeacher> records = pageTeacher.getRecords();//每页数据
        long total = pageTeacher.getTotal();//总记录数
        long size = pageTeacher.getSize();//每页显示记录数
        long pages = pageTeacher.getPages();//总页数
        long current = pageTeacher.getCurrent();//当前页
        boolean hasNext = pageTeacher.hasNext();//是否有下一页
        boolean hasPrevious = pageTeacher.hasPrevious();//是否有上一页

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

    @Override
    public List<EduCourse> getCourseListByTeacherId(String id) {
        //拼接条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",id);
        List<EduCourse> list = eduCourseService.list(wrapper);
        return list;
    }
}
