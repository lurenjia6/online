package com.online.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.common.R;
import com.online.edu.entity.EduTeacher;
import com.online.edu.entity.query.QueryTeacher;
import com.online.edu.service.IEduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author admin
 * @since 2020-01-02
 */
@CrossOrigin//解决跨域请求
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    //注入service
    @Autowired
    private IEduTeacherService eduTeacherService;

    //1.查询所有的讲师功能
    @GetMapping
    public R getAllTeacherList() {

        //1.调用service方法
        List<EduTeacher> list = eduTeacherService.list(null);

        return R.ok().data("items", list);
    }

    //2.逻辑删除讲师
    @DeleteMapping("{id}")
    public R deleteTeacherById(@PathVariable("id") String id) {

         eduTeacherService.removeById(id);

        return R.ok();
    }

    //3.分页查询讲师列表的方法,page当前页,limit记录数
    @GetMapping("pageList/{page}/{limit}")
    public R getPageTeacherList(@PathVariable("page") Long page,
                                @PathVariable("limit") Long limit) {

        //创建page对象,传递两个参数,page当前页,limit记录数
        Page<EduTeacher> pageTeacher = new Page<>(page, limit);

        //调用方法实现分页查询
        eduTeacherService.page(pageTeacher, null);

        //从pageTeacher对象里面获取分页数据
        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//每页的记录数

        return R.ok().data("total", total).data("items", records);
    }

    //4.多条件组合查询带分页
    @PostMapping("moreCondtionPageList/{page}/{limit}")
    public R getMoreCondtionPageList(@PathVariable Long page,
                                     @PathVariable Long limit,
                                     @RequestBody(required = false) QueryTeacher queryTeacher) {
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);

        eduTeacherService.pageListCondition(pageTeacher,queryTeacher);

        //从pageTeacher对象里面获取分页数据
        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//每页的记录数

        return R.ok().data("total", total).data("items", records);
    }

    //5.添加讲师的方法
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if(save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //6.根据id查询讲师
    @GetMapping("getTeacherInfo/{id}")
    public R getTeacherInfo(@PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("eduTeacher",eduTeacher);
    }

    //7.根据id修改的方法
    @PostMapping("updateTeacher/{id}")
    public R updateTeacher(@PathVariable String id,
                           @RequestBody EduTeacher eduTeacher){
        boolean b = eduTeacherService.updateById(eduTeacher);
        if(b){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //模拟登陆
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]")
                .data("name","admin")
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

}

