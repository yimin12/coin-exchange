package com.yimin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yimin.domain.Config;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/13 17:32
*   @Description : 
*
*/
    
public interface ConfigService extends IService<Config>{

    /**
     * 条件分页查询参数
     * @param page
     *分页参数
     * @param type
     * 类型
     * @param name
     * 参数名称
     * @param code
     * 参数Code
     * @return
     */
    Page<Config> findByPage(Page<Config> page, String type, String code, String name);
}
