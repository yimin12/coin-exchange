package com.yimin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yimin.domain.Config;
import com.yimin.mapper.ConfigMapper;
import com.yimin.service.ConfigService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/16 22:04
*   @Description : 
*
*/
    
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService{

    /**
     * 条件分页查询参数
     *
     * @param page 分页参数
     * @param type 类型
     * @param name 参数名称
     * @param code 参数Code
     * @return
     */
    @Override
    public Page<Config> findByPage(Page<Config> page, String type, String name, String code) {
        return page(page,new LambdaQueryWrapper<Config>()
                .like(!StringUtils.isEmpty(type),Config::getType ,type)
                .like(!StringUtils.isEmpty(name),Config::getName ,name)
                .like(!StringUtils.isEmpty(code),Config::getCode ,code)
        );
    }

    /**
     * 通过的规则的Code 查询签名
     *
     * @param code 签名的code
     * @return config value
     */
    @Override
    public Config getConfigByCode(String code) {
        return getOne(new LambdaQueryWrapper<Config>().eq(Config::getCode,code));
    }
}
