package com.yimin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.WebConfig;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/13 17:32
*   @Description : 
*
*/
    
public interface WebConfigService extends IService<WebConfig>{

    /**
     * 条件分页查询
     *
     * @param page 分页参数
     * @param name webConfig的名称
     * @param type webConfig的类型
     * @return
     */
    Page<WebConfig> findByPage(Page<WebConfig> page, String name, String type);

    /**
     * 查询pc端的banner
     * @return
     */
    List<WebConfig> getBanners();
}
