package com.yimin.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/12 21:19
 *   @Description :
 *
 */
@Component
public class AutoFillHeader implements MetaObjectHandler {

    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     * 新增数据时要添加的为：
     * 1 创建人
     * 2 创建时间
     * 3 lastupdatetime
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = getCurrentUserId();
        this.strictInsertFill(metaObject, "createBy", Long.class, userId); // 自动插入创建人
        this.strictInsertFill(metaObject, "created", Date.class, new Date()); // 自动插入时间
        this.strictInsertFill(metaObject, "lastUpdateTime", Date.class, new Date()); // 修改时间
    }

    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     * //1 修改人
     * // 2 修改时间
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
       Long userId = getCurrentUserId();
       this.strictUpdateFill(metaObject, "modifyBy", Long.class, userId); // 自动更新修改人
       this.strictUpdateFill(metaObject, "lastUpdateTime", Date.class, new Date());
    }

    /**
     * 获取当前操作者的用户对象
     * @return
     */
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            String s = authentication.getPrincipal().toString();
            if("anonymousUser".equals(s)){ // 在用户没有登录的时候默认使用这个用户
                return null;
            }
            return Long.valueOf(s);
        }
        return null;
    }


}
