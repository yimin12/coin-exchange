package com.yimin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.mapper.ConfigMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yimin.service.ConfigService;
import com.yimin.domain.Config;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/13 17:32
*   @Description : 
*
*/
    
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService{


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
    @Override
    public Page<Config> findByPage(Page<Config> page, String type, String code, String name) {
        return page(page, new LambdaQueryWrapper<Config>()
                .like(!StringUtils.isEmpty(type), Config::getType, type)
                .like(!StringUtils.isEmpty(code), Config::getCode, code)
                .like(!StringUtils.isEmpty(name), Config::getName, name));
    }
}
