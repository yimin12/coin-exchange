package com.yimin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.Coin;
import com.yimin.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yimin.mapper.AdminAddressMapper;
import com.yimin.domain.AdminAddress;
import com.yimin.service.AdminAddressService;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/17 16:13
*   @Description : 
*
*/
    
@Service
public class AdminAddressServiceImpl extends ServiceImpl<AdminAddressMapper, AdminAddress> implements AdminAddressService{

    @Autowired
    private CoinService coinService ;
    /**
     * 条件分页查询归集地址
     *
     * @param page 分页参数
     * @param coinId 币种的Id
     * @return
     */
    @Override
    public Page<AdminAddress> findByPage(Page<AdminAddress> page, Long coinId) {
        return page(page,new LambdaQueryWrapper<AdminAddress>().eq(coinId!=null ,AdminAddress::getCoinId,coinId));
    }

    /**
     * 重新save ,为了让我们的归集地址里面包含coinType
     * @param entity
     * @return
     */
    @Override
    public boolean save(AdminAddress entity) {
        Long coinId = entity.getCoinId();
        Coin coin = coinService.getById(coinId);
        if(coin==null){
            throw new IllegalArgumentException("输入的币种id 错误") ;
        }
        String type = coin.getType();
        entity.setCoinType(type);
        return super.save(entity) ;
    }
}
