package com.yimin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.EntrustOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yimin.domain.ExchangeTrade;
import com.yimin.param.OrderParam;
import com.yimin.vo.TradeEntrustOrderVo;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/17 14:53
*   @Description : 
*
*/
    
public interface EntrustOrderService extends IService<EntrustOrder>{


    /**
     * 分页查询委托单
     * @param page
     *  分页参数
     * @param userId
     *      用户的id
     * @param symbol
     *      交易对
     * @param type
     *      交易类型
     * @return
     */
    Page<EntrustOrder> findByPage(Page<EntrustOrder> page, Long userId, String symbol, Integer type);


    /**
     * 获取用户的历史委托记录
     * @param page
     *      分页参数
     * @param symbol
     *          交易对
     * @param userId
     *          用户的Id
     * @return
     */
    Page<TradeEntrustOrderVo> getHistoryEntrustOrder(Page<EntrustOrder> page, String symbol, Long userId);

    /**
     * 查询未完成的委托单
     * @param page
     * @param symbol
     * @param userId
     * @return
     */
    Page<TradeEntrustOrderVo> getEntrustOrder(Page<EntrustOrder> page, String symbol, Long userId);

    /**
     * 创建一个新的委托大
     * @param userId
     * 用户的id
     * @param orderParam
     * 委托单的数据
     * @return
     */
    Boolean createEntrustOrder(Long userId, OrderParam orderParam);


    /**
     * 更新我们的委托单的数据
     * @param exchangeTrade
     */
    void doMatch(ExchangeTrade exchangeTrade);

    /**
     * 将数据投递到MQ 里面
     * @param orderId
     */
    void cancleEntrustOrder(Long orderId);

    /**
     * 数据库里面委托单的取消
     * @param orderId
     */
    void cancleEntrustOrderToDb(String orderId);
}
