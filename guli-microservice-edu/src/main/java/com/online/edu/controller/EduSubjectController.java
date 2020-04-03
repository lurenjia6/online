package com.online.edu.controller;


import com.online.edu.common.R;
import com.online.edu.entity.EduSubject;
import com.online.edu.entity.dto.OneSubjectDto;
import com.online.edu.service.IEduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author admin
 * @since 2020-01-31
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Autowired
    private IEduSubjectService eduSubjectService;


    //5 添加二级分类
    @PostMapping("addTwoLevel")
    public R addTwoLevel(@RequestBody EduSubject eduSubject) {
        boolean flag = eduSubjectService.saveTwoLevel(eduSubject);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //4 添加一级分类
    @PostMapping("addOneLevel")
    public R addOneLevel(@RequestBody EduSubject eduSubject) {
        boolean flag = eduSubjectService.saveOneLevel(eduSubject);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //3 删除一级分类
    @DeleteMapping("{id}")
    public R deleteSubjectId(@PathVariable String id) {
        boolean flag = eduSubjectService.deleteSubjectById(id);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //2 返回所有分类数据，返回要求的json数据格式
    @GetMapping
    public R getAllSubjectList() {
        List<OneSubjectDto> list = eduSubjectService.getSubjectList();
        return R.ok().data("OneSubjectDto",list);
    }

    //通过上传excel文件获取文件内容
    @PostMapping("import")
    public R importExcelSubject(@RequestParam("file")MultipartFile file){
        //1.获取上传excel文件
        List<String> strings = eduSubjectService.importSubject(file);

        if(strings.size() == 0){
            return R.ok();
        }else {
            return R.error().data("megList",strings);
        }


    }
}

