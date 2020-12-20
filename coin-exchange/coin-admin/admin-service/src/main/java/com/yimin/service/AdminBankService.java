package com.yimin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.AdminBank;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yimin.dto.AdminBankDto;

import java.util.List;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/14 23:45
*   @Description : 
*
*/
    
public interface AdminBankService extends IService<AdminBank>{

    /**
     * 条件分页查询公司银行卡
     * @param page
     * 分页参数
     * @param bankCard
     * 银行卡卡号
     * @return
     */
    Page<AdminBank> findByPage(Page<AdminBank> page, String bankCard);

    /**
     * 查询所有银行的开启信息
     * @return
     */
    List<AdminBankDto> getAllAdminBanks();
}
