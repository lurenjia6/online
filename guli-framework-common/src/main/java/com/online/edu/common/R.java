package com.online.edu.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义具体的数据返回格式
 */
@Data
@ApiModel(value = "全局统一返回结果")
public class R {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;//是否成功

    @ApiModelProperty(value = "返回码")
    private Integer code;//状态码

    @ApiModelProperty(value = "返回消息")
    private String message;//返回消息

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<>();//返回数据

    private R() {
    }

    //操作成功，调用这个方法,返回成功的数据
    public static R ok() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("操作成功");
        return r;
    }

    //操作失败，调用这个方法,返回失败的数据
    public static R error() {
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("操作失败");
        return r;

    }

    /**
     * 链式编程，R.success().message
     * @return tish当前对象，不用new了
     */

    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public R message(String message) {
        this.setMessage(message);
        return this;
    }


    public R code(Integer code) {
        this.setCode(code);
        return this;
    }


    public R data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

}

