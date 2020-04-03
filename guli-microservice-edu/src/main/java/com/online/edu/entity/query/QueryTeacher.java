package com.online.edu.entity.query;

import lombok.Data;

/**
 * 讲师列表多条件组合查询带分页,用于封装条件值
 */
@Data
public class QueryTeacher {

    private String name;//名字
    private String level;//级别
    private String begin;//开始时间
    private String end;//结束时间
}
