package com.online.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.common.R;
import com.online.edu.entity.EduCourse;
import com.online.edu.entity.dto.CourseInfoDto;
import com.online.edu.entity.form.CourseInfoFrom;
import com.online.edu.entity.query.QueryCourse;
import com.online.edu.service.IEduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author admin
 * @since 2020-02-01
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private IEduCourseService courseService;

    //最终发布课程的方法,修改课程状态码
    @GetMapping("publishCourse/{courseId}")
    public R publishCourse(@PathVariable String courseId){

        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");

        boolean result = courseService.updateById(eduCourse);

        if(result) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //根据课程id查询课程详细信息
    @GetMapping("getAllCourseInfo/{courseId}")
    public R getAllCourseInfo(@PathVariable String courseId){

        CourseInfoDto courseInfoDto = courseService.getCourseInfoAll(courseId);

        return R.ok().data("courseInfo",courseInfoDto);
    }

    //删除课程的方法
    @DeleteMapping("deleteCourse/{id}")
    public R deleteCourse(@PathVariable String id){
       boolean flag =  courseService.removeCourseId(id);

        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //课程列表
    // 完善列表带分页，带条件查询(课程价格，名称)
    @PostMapping("listCourse/{page}/{limit}")
    public R getCourseList(@PathVariable Long page,
                           @PathVariable Long limit,
                           @RequestBody(required = false) QueryCourse queryCourse){
        /**
         * 条件查询带分页实现步骤
         * 1.new一个page对象,page(页数),limit(条数)，传进去
         * 2.在service层中写具体的实现，controller层中调用一下service层中方法
         * 3.最后返回总业数和每页记录数
         */
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        courseService.pageListCourse(pageCourse,queryCourse);

        //从pageTeacher对象里面获取分页数据
        long total = pageCourse.getTotal();//总记录数
        List<EduCourse> records = pageCourse.getRecords();//每页的记录数据

        return R.ok().data("total", total).data("items", records);
    }


    //修改课程的方法
    @PostMapping("updateCourseInfo/{id}")
    public R updateCourseInfo(@PathVariable String id,
                              @RequestBody CourseInfoFrom courseInfoFrom){

        Boolean flag = courseService.updateCourse(courseInfoFrom);

        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }


    //根据课程id查询课程信息
    @GetMapping("getCourseInfo/{id}")
    public R getCourseInfo(@PathVariable String id){

        CourseInfoFrom courseInfoFrom = courseService.getIdCourse(id);

        return R.ok().data("courseInfoFrom",courseInfoFrom);
    }

    //1.添加课程信息的方法
    @PostMapping
    public R addCourseInfo(@RequestBody CourseInfoFrom courseInfoFrom){

        String courseId = courseService.insertCourseInfo(courseInfoFrom);


            return R.ok().data("courseId",courseId);

    }
}

