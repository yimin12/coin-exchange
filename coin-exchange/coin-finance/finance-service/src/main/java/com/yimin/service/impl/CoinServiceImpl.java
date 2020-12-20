package com.yimin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.dto.CoinDto;
import com.yimin.mappers.CoinMappersDto;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yimin.domain.Coin;
import com.yimin.mapper.CoinMapper;
import com.yimin.service.CoinService;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/17 15:06
*   @Description : 
*
*/
    
@Service
public class CoinServiceImpl extends ServiceImpl<CoinMapper, Coin> implements CoinService{

    /**
     * 数字货币的条件分页查询
     *
     * @param name 数字货币的名称
     * @param type 数字货币类型的名称
     * @param status 数字货币的状态
     * @param title 字货币的标题
     * @param walletType 树字货币的钱包类型名称
     * @param page 分页参数
     * @return 数据货币的分页数据
     */
    @Override
    public Page<Coin> findByPage(String name, String type, Byte status, String title, String walletType, Page<Coin> page)
    {
        return page(page,
                new LambdaQueryWrapper<Coin>()
                        .like(!StringUtils.isEmpty(name), Coin::getName, name) // 名称的查询
                        .like(!StringUtils.isEmpty(title), Coin::getTitle, title) // 标题的查询
                        .eq(status != null, Coin::getStatus, status) // 状态的查询
                        .eq(!StringUtils.isEmpty(type), Coin::getType, type) // 货币类型名称的查询
                        .eq(!StringUtils.isEmpty(walletType), Coin::getWallet, walletType) // 货币钱包类型的查询
        );
    }
    /**
     * 使用币种的状态查询所有的币种信息
     *
     * @param status
     * @return
     */
    @Override
    public List<Coin> getCoinsByStatus(Byte status) {
        return list(new LambdaQueryWrapper<Coin>().eq(Coin::getStatus,status));
    }


    /**
     * 使用货币的名称来查询货币
     * 货币的名称是唯一
     * @param coinName
     *
     * @return
     */
    @Override
    public Coin getByCoinName(String coinName) {
       return getOne(new LambdaQueryWrapper<Coin>().eq(Coin::getName, coinName));
    }

    @Override
    public List<CoinDto> findList(List<Long> coinIds) {
        List<Coin> coins = super.listByIds(coinIds);
        if(CollectionUtils.isEmpty(coinIds)){
            return Collections.emptyList() ;
        }
        List<CoinDto> coinDtos = CoinMappersDto.INSTANCE.toConvertDto(coins);
        return coinDtos;
    }
}
