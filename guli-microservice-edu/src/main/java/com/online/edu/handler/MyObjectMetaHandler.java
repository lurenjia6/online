package com.online.edu.handler;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;
/**
 * 自动填充创建时间和修改时间、乐观锁版本号
 * 1.在实体类里面加上@TableField(fill = FieldFill.INSERT_UPDATE)注解
 * 2.创建一个类，实现MetaObjectHandler接口，重写方法
 */
@Component
public class MyObjectMetaHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("gmtCreate",new Date(),metaObject);
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }
}
