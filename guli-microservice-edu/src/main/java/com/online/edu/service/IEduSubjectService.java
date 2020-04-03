package com.online.edu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.entity.EduSubject;
import com.online.edu.entity.dto.OneSubjectDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author admin
 * @since 2020-01-31
 */
public interface IEduSubjectService extends IService<EduSubject> {

    //读取excel内容
    List<String> importSubject(MultipartFile file);


    //返回所有分类
    List<OneSubjectDto> getSubjectList();

    //删除分类
    boolean deleteSubjectById(String id);

    //添加一级分类
    boolean saveOneLevel(EduSubject eduSubject);

    //添加二级分类
    boolean saveTwoLevel(EduSubject eduSubject);
}
