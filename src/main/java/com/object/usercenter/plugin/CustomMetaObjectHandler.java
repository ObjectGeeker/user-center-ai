package com.object.usercenter.plugin;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createBy", String.class, (String) StpUtil.getLoginId());
        this.strictInsertFill(metaObject, "updateBy", String.class, (String) StpUtil.getLoginId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateBy", String.class, (String) StpUtil.getLoginId());
    }
}
