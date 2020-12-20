package com.yimin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.CoinType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/17 15:06
*   @Description : 
*
*/
    
public interface CoinTypeService extends IService<CoinType>{


    /**
     * 条件分页查询货币类型
     * @param page
     * 分页参数
     * @param code
     * 类型的Code
     * @return
     * 分页货币类型
     */
    Page<CoinType> findByPage(Page<CoinType> page, String code);

    /**
     * 使用币种类型的状态查询所有的币种类型值
     * @param status
     * @return
     */
    List<CoinType> listByStatus(Byte status);
}

