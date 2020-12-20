package com.yimin.service;

import com.yimin.domain.CoinConfig;
import com.baomidou.mybatisplus.extension.service.IService;
    
/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/17 15:06
*   @Description : 
*
*/
    
public interface CoinConfigService extends IService<CoinConfig>{


    /**
     * 通过币种的id 查询币种的配置信息
     * @param coinId
     * 币种的id
     * @return
     * 币种的配置信息
     */
    CoinConfig findByCoinId(Long coinId);

    /**
     * 新增或修改币种配置
     * @param coinConfig
     * @return
     */
    boolean updateOrSave(CoinConfig coinConfig);
}
