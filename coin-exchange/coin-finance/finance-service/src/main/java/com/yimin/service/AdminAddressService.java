package com.yimin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.AdminAddress;
import com.baomidou.mybatisplus.extension.service.IService;
    
/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/17 16:13
*   @Description : 
*
*/
    
public interface AdminAddressService extends IService<AdminAddress>{

    /**
     * 条件分页查询归集地址
     * @param page
     * 分页参数
     * @param coinId
     * 币种的Id
     * @return
     */
    Page<AdminAddress> findByPage(Page<AdminAddress> page, Long coinId);
}
