package com.online.edu.statistics.service;

import com.online.edu.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author admin
 * @since 2020-02-06
 */
public interface IDailyService extends IService<Daily> {
    void getCountRegisterNum(String day);

    //返回图表显示使用的数据
    Map<String, Object> getDataCount(String type, String begin, String end);
}
