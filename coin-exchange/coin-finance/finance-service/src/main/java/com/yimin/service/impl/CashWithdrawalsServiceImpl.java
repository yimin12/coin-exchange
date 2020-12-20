package com.yimin.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.Account;
import com.yimin.domain.CashRecharge;
import com.yimin.domain.Config;
import com.yimin.dto.UserBankDto;
import com.yimin.dto.UserDto;
import com.yimin.feign.UserBankServiceFeign;
import com.yimin.feign.UserServiceFeign;
import com.yimin.model.CashSellParam;
import com.yimin.service.AccountService;
import com.yimin.service.ConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yimin.domain.CashWithdrawals;
import com.yimin.mapper.CashWithdrawalsMapper;
import com.yimin.service.CashWithdrawalsService;
import org.springframework.util.CollectionUtils;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/17 15:06
*   @Description : 
*
*/
    
@Service
public class CashWithdrawalsServiceImpl extends ServiceImpl<CashWithdrawalsMapper, CashWithdrawals> implements CashWithdrawalsService{

    @Autowired
    private UserServiceFeign userServiceFeign;

    @Autowired
    private ConfigService configService ;


    @Autowired
    private StringRedisTemplate redisTemplate ;


    @Autowired
    private UserBankServiceFeign userBankServiceFeign ;


    @Autowired
    private AccountService accountService ;


    /**
     * 提现记录的查询
     * @param page
     * 分页数据
     * @param userId
     * 用户的id
     * @param userName
     * 用户的名称
     * @param mobile
     * 用户的手机号
     * @param status
     * 提现的状态
     * @param numMin
     * 提现的最小金额
     * @param numMax
     * 提现的最大金额
     * @param startTime
     * 提现的开始时间
     * @param endTime
     * 提现的截至时间
     * @return
     */
    @Override
    public Page<CashWithdrawals> findByPage(Page<CashWithdrawals> page, Long userId, String userName, String mobile, Byte status, String numMin, String numMax, String startTime, String endTime) {
        // 当由用户的信息时
        Map<Long, UserDto> basicUsers = null;
        LambdaQueryWrapper<CashWithdrawals> cashWithdrawalsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(userId != null || !StringUtils.isEmpty(userName) || !StringUtils.isEmpty(mobile)){
            basicUsers = userServiceFeign.getBasicUsers(userId == null ? null : Arrays.asList(userId), userName, mobile);
            if(CollectionUtils.isEmpty(basicUsers)){
                return page;
            }
            Set<Long> userIds = basicUsers.keySet();
            cashWithdrawalsLambdaQueryWrapper.in(CashWithdrawals::getUserId, userIds);
        }
        // 其他的查询信息
        cashWithdrawalsLambdaQueryWrapper.eq(status != null, CashWithdrawals::getStatus, status)
                .between(
                        !(StringUtils.isEmpty(numMin) || StringUtils.isEmpty(numMax)),
                        CashWithdrawals::getNum,
                        new BigDecimal(StringUtils.isEmpty(numMin) ? "0" : numMin),
                        new BigDecimal(StringUtils.isEmpty(numMax) ? "0" : numMax)
                )
                .between(
                        !(StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)),
                        CashWithdrawals::getCreated,
                        startTime, endTime + " 23:59:59"
                );
        Page<CashWithdrawals> pageDate = page(page, cashWithdrawalsLambdaQueryWrapper);
        List<CashWithdrawals> records = pageDate.getRecords();
        if (!CollectionUtils.isEmpty(records)) {
            List<Long> userIds = records.stream().map(CashWithdrawals::getUserId).collect(Collectors.toList());
            if (basicUsers == null) {
                basicUsers = userServiceFeign.getBasicUsers(userIds, null, null);
            }
            Map<Long, UserDto> finalBasicUsers = basicUsers;
            records.forEach(cashWithdrawals -> {
                UserDto userDto = finalBasicUsers.get(cashWithdrawals.getUserId());
                if (userDto != null) {
                    cashWithdrawals.setUsername(userDto.getUsername());
                    cashWithdrawals.setRealName(userDto.getRealName());
                }
            });
        }
        return pageDate;
    }

    /**
     * 查询当前用户的充值的数据
     *
     * @param page 分页对象
     * @param userId 用户的Id
     * @param status 订单的状态
     * @return
     */
    @Override
    public Page<CashWithdrawals> findCashWithdrawals(Page<CashWithdrawals> page, Long userId, Byte status) {
        return page(page,new LambdaQueryWrapper<CashWithdrawals>()
                .eq(CashWithdrawals::getUserId,userId)
                .eq(status!=null,CashWithdrawals::getStatus,status));
    }

    /**
     * GCN的卖出操作
     * @param userId
     * @param cashSellParam
     * @return
     */
    @Override
    public boolean sell(Long userId, CashSellParam cashSellParam) {
        // 1. 校验参数
        checkCashSellParam(cashSellParam);
        Map<Long, UserDto> basicUsers = userServiceFeign.getBasicUsers(Arrays.asList(userId), null, null);
        if(CollectionUtils.isEmpty(basicUsers)){
            throw new IllegalArgumentException("用户的id错误");
        }
        UserDto userDto = basicUsers.get(userId);
        // 2. 手机验证码
        validatePhoneCode(userDto.getMobile(), cashSellParam.getValidateCode());
        // 3. 验证支付密码
        checkUserPayPassword(userDto.getPaypassword(), cashSellParam.getPayPassword());
        // 4. 远程调用查询用户的银行卡
        UserBankDto userBankInfo = userBankServiceFeign.getUserBankInfo(userId);
        if (userBankInfo == null) {
            throw new IllegalArgumentException("该用户暂未绑定银行卡信息");
        }
        String remark = RandomUtil.randomNumbers(6);
        // 5. 通过数量得到本次交易的金额
        BigDecimal amount = getCashWithdrawalsAmount(cashSellParam.getNum());
        // 6. 计算本次交易的手续费用
        BigDecimal fee = getCashWithdrawalsFee(amount);
        // 7. 检查用户的账户ID
        Account account = accountService.findByUserAndCoin(userId, "GCN");
        // 8. 订单的创建
        CashWithdrawals cashWithdrawals = new CashWithdrawals();
        cashWithdrawals.setUserId(userId);
        cashWithdrawals.setAccountId(account.getId());
        cashWithdrawals.setCoinId(cashSellParam.getCoinId());
        cashWithdrawals.setStatus((byte) 0);
        cashWithdrawals.setStep((byte) 1);
        cashWithdrawals.setNum(cashSellParam.getNum());
        cashWithdrawals.setMum(amount.subtract(fee)); // 实际金额= amount-fee
        cashWithdrawals.setFee(fee);
        cashWithdrawals.setBank(userBankInfo.getBank());
        cashWithdrawals.setBankCard(userBankInfo.getBankCard());
        cashWithdrawals.setBankAddr(userBankInfo.getBankAddr());
        cashWithdrawals.setBankProv(userBankInfo.getBankProv());
        cashWithdrawals.setBankCity(userBankInfo.getBankCity());
        cashWithdrawals.setTruename(userBankInfo.getRealName());
        cashWithdrawals.setRemark(remark);
        boolean save = save(cashWithdrawals);
        if(save){
            // 扣减总资产，需要保证事务性
            accountService.lockUserAmount(userId, cashWithdrawals.getCoinId(), cashWithdrawals.getMum(), "withdrawals_out", cashWithdrawals.getId(), cashWithdrawals.getFee());
        }
        return save;
    }

    /**
     * 计算本次的手续费
     *
     * @param amount
     * @return
     */
    private BigDecimal getCashWithdrawalsFee(BigDecimal amount){
        // 1 通过总金额* 费率= 手续费
        // 2 若金额较小---->最小的提现的手续费
        // 最小的提现费用
        Config withdrawMinPoundage = configService.getConfigByCode("WITHDRAW_MIN_POUNDAGE");
        BigDecimal withdrawMinPoundageFee = new BigDecimal(withdrawMinPoundage.getValue());
        // 体现的费率
        Config withdrawPoundageRate = configService.getConfigByCode("WITHDRAW_POUNDAGE_RATE");
        // 通过费率计算手续费
        BigDecimal poundageFee = amount.multiply(new BigDecimal(withdrawPoundageRate.getValue())).setScale(2, RoundingMode.HALF_UP);
        // min 取两个的最小值
        return poundageFee.min(withdrawMinPoundageFee).equals(poundageFee) ? withdrawMinPoundageFee : poundageFee;
    }

    /**
     * 通过数量计算金额
     *
     * @param num
     * @return
     */
    private BigDecimal getCashWithdrawalsAmount(BigDecimal num) {
        //
        Config rateConfig = configService.getConfigByCode("USDT2CNY");
        return num.multiply(new BigDecimal(rateConfig.getValue())).setScale(2,
                RoundingMode.HALF_UP);
    }

    /**
     * 支付密码的校验
     *
     * @param payDBPassword
     * @param payPassword
     */
    private void checkUserPayPassword(String payDBPassword, String payPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean matches = bCryptPasswordEncoder.matches(payPassword,
                payDBPassword);
        if (!matches) {
            throw new IllegalArgumentException("支付密码错误");
        }
    }

    /**
     * 校验用户的手机验证码
     *
     * @param mobile
     * @param validateCode
     */
    private void validatePhoneCode(String mobile, String validateCode) {
        // 验证:SMS:CASH_WITHDRAWS:mobile
        String code = redisTemplate.opsForValue().get("SMS:CASH_WITHDRAWS:" +
                mobile);
        if (!validateCode.equals(code)) {
            throw new IllegalArgumentException("验证码错误");
        }
    }

    /**
     * 1 手机验证码
     * 2 支付密码
     * 3 提现相关的验证
     *
     * @param cashSellParam
     */
    private void checkCashSellParam(CashSellParam cashSellParam) {
        // 1 提现状态
        Config cashWithdrawalsStatus =
                configService.getConfigByCode("WITHDRAW_STATUS");
        if (Integer.valueOf(cashWithdrawalsStatus.getValue()) != 1) {
            throw new IllegalArgumentException("提现暂未开启");
        }
        // 2 提现的金额
        @NotNull BigDecimal cashSellParamNum = cashSellParam.getNum();
        // 2.1 最小的提现额度100
        Config cashWithdrawalsConfigMin = configService.getConfigByCode("WITHDRAW_MIN_AMOUNT");
        //101
        if (cashSellParamNum.compareTo(new BigDecimal(cashWithdrawalsConfigMin.getValue())) < 0) {
            throw new IllegalArgumentException("检查提现的金额");
        }
        // 2.1 最小的提现额度200
        // 201
        Config cashWithdrawalsConfigMax =
                configService.getConfigByCode("WITHDRAW_MAX_AMOUNT");
        if (cashSellParamNum.compareTo(new
                BigDecimal(cashWithdrawalsConfigMax.getValue())) >= 0) {
            throw new IllegalArgumentException("检查提现的金额");
        }
    }
}
