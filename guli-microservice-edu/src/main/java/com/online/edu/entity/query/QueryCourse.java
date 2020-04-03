package com.online.edu.entity.query;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 课程列表多条件组合查询带分页,用于封装条件值
 */
@Data
public class QueryCourse {

    private String title;//课程名称
    private BigDecimal price;//课程价格
    private String begin;//开始时间
    private String end;//结束时间

}
