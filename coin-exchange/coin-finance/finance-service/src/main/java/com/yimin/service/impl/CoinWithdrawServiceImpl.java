package com.yimin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.dto.UserDto;
import com.yimin.feign.UserServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yimin.mapper.CoinWithdrawMapper;
import com.yimin.domain.CoinWithdraw;
import com.yimin.service.CoinWithdrawService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/17 15:06
*   @Description : 
*
*/
    
@Service
public class CoinWithdrawServiceImpl extends ServiceImpl<CoinWithdrawMapper, CoinWithdraw> implements CoinWithdrawService{

    @Autowired
    private UserServiceFeign userServiceFeign ;
    /**
     * 分页条件查询充值记录
     *
     * @param page      分页参数
     * @param coinId    币种的Id
     * @param userId    用户的Id
     * @param userName  用户的名称
     * @param mobile    用户的手机号
     * @param status    充值的状态
     * @param numMin    充值的最小金额
     * @param numMax    充值的最大金额
     * @param startTime 充值的开始时间
     * @param endTime   充值的截至时间
     * @return
     */
    @Override
    public Page<CoinWithdraw> findByPage(Page<CoinWithdraw> page, Long coinId, Long userId, String userName, String mobile, Byte status, String numMin, String numMax, String startTime, String endTime) {
        LambdaQueryWrapper<CoinWithdraw> coinWithdrawLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 1 若用户本次的查询中,带了用户的信息userId, userName,mobile ----> 本质就是要把用户的Id 放在我们的查询条件里面
        Map<Long, UserDto> basicUsers = null;
        if (userId != null || !StringUtils.isEmpty(userName) | !StringUtils.isEmpty(mobile)) { // 使用用户的信息查询
            // 需要远程调用查询用户的信息
            basicUsers = userServiceFeign.getBasicUsers(userId == null ? null : Arrays.asList(userId), userName, mobile);
            if (CollectionUtils.isEmpty(basicUsers)) { // 找不到这样的用户->
                return page;
            }
            Set<Long> userIds = basicUsers.keySet();
            coinWithdrawLambdaQueryWrapper.in(!CollectionUtils.isEmpty(userIds), CoinWithdraw::getUserId, userIds);
        }
        // 2 若用户本次的查询中,没有带了用户的信息
        coinWithdrawLambdaQueryWrapper.eq(coinId != null, CoinWithdraw::getCoinId, coinId)
                .eq(status != null, CoinWithdraw::getStatus, status)
                .between(
                        !(StringUtils.isEmpty(numMin) || StringUtils.isEmpty(numMax)),
                        CoinWithdraw::getNum,
                        new BigDecimal(StringUtils.isEmpty(numMin) ? "0" : numMin),
                        new BigDecimal(StringUtils.isEmpty(numMax) ? "0" : numMax)
                )
                .between(
                        !(StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)),
                        CoinWithdraw::getCreated,
                        startTime, endTime + " 23:59:59"
                );
        Page<CoinWithdraw> coinWithdrawPage = page(page, coinWithdrawLambdaQueryWrapper);
        List<CoinWithdraw> records = coinWithdrawPage.getRecords();
        if (!CollectionUtils.isEmpty(records)) {
            List<Long> userIds = records.stream().map(CoinWithdraw::getUserId).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(basicUsers)) {
                basicUsers = userServiceFeign.getBasicUsers(userIds, null, null);
            }
            Map<Long, UserDto> finalBasicUsers = basicUsers;
            records.forEach(coinWithdraw -> { // 设置用户相关的数据
                UserDto userDto = finalBasicUsers.get(coinWithdraw.getUserId());
                if (userDto != null) {
                    coinWithdraw.setUsername(userDto.getUsername()); // 远程调用查询用户的信息
                    coinWithdraw.setRealName(userDto.getRealName());
                }
            });
        }
        return coinWithdrawPage ;
    }

    /***
     * 查询用户的提币记录
     * @param userId
     * @param coinId
     * @param page
     * @return
     */
    @Override
    public Page<CoinWithdraw> findUserCoinWithdraw(Long userId, Long coinId, Page<CoinWithdraw> page) {
        return page(page,new LambdaQueryWrapper<CoinWithdraw>()
                .eq(CoinWithdraw::getUserId,userId)
                .eq(coinId!=null,CoinWithdraw::getCoinId,coinId)
        );
    }
}
