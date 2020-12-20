package com.yimin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yimin.domain.AdminBank;
import com.yimin.dto.AdminBankDto;
import com.yimin.mapper.AdminBankMapper;
import com.yimin.mappers.AdminBankDtoMappers;
import com.yimin.service.AdminBankService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;


/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/14 23:45
*   @Description : 
*
*/
    
@Service
public class AdminBankServiceImpl extends ServiceImpl<AdminBankMapper, AdminBank> implements AdminBankService{



    /**
     * 条件分页查询公司银行卡
     * @param page
     * 分页参数
     * @param bankCard
     * 银行卡卡号
     * @return
     */
    @Override
    public Page<AdminBank> findByPage(Page<AdminBank> page, String bankCard) {
        return page(page, new LambdaQueryWrapper<AdminBank>()
                .like(!StringUtils.isEmpty(bankCard), AdminBank::getBankCard, bankCard));
    }

    /**
     * 查询所有的银行开启信息
     *
     * @return
     */
    @Override
    public List<AdminBankDto> getAllAdminBanks() {
        List<AdminBank> adminBanks = list(new LambdaQueryWrapper<AdminBank>().eq(AdminBank::getStatus, 1));
        if (CollectionUtils.isEmpty(adminBanks)){
            return Collections.emptyList() ;
        }
        List<AdminBankDto> adminBankDtos = AdminBankDtoMappers.INSTANCE.toConvertDto(adminBanks);
        return adminBankDtos;
    }
}
