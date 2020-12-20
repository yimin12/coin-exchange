package com.yimin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yimin.domain.*;
import com.yimin.dto.MarketDto;
import com.yimin.feign.MarketServiceFeign;
import com.yimin.mappers.AccountVoMappers;
import com.yimin.service.AccountDetailService;
import com.yimin.service.CoinService;
import com.yimin.service.ConfigService;
import com.yimin.vo.AccountVo;
import com.yimin.vo.SymbolAssetVo;
import com.yimin.vo.UserTotalAccountVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yimin.mapper.AccountMapper;
import com.yimin.service.AccountService;
import org.springframework.util.CollectionUtils;

/*
*   @Author : Yimin Huang
*   @Contact : hymlaucs@gmail.com
*   @Date : 2020/12/17 15:06
*   @Description : 
*
*/
    
@Service
@Slf4j
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService{

    @Autowired
    private CoinService coinService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private AccountDetailService accountDetailService;

    @Autowired
    private MarketServiceFeign marketServiceFeign;

    /**
     * 查询某个用户的货币资产
     *
     * @param userId 用户的id
     * @param coinName 货币的名称
     * @return
     */
    @Override
    public Account findByUserAndCoin(Long userId, String coinName) {
        Coin coin = coinService.getByCoinName(coinName);
        if(coin == null){
            throw new IllegalArgumentException("货币不存在");
        }
        Account account = getOne(new LambdaQueryWrapper<Account>().eq(Account::getUserId, userId).eq(Account::getCoinId, coin.getId()));
        if(account == null){
            throw new IllegalArgumentException("该资产不存在");
        }
        Config sellRateConfig = configService.getConfigByCode("USDT2CNY");
        account.setSellRate(new BigDecimal(sellRateConfig.getValue()));

        Config setBuyRateConfig = configService.getConfigByCode("CNY2USDT");
        account.setBuyRate(new BigDecimal(setBuyRateConfig.getValue())); // 买进来的费率
        return account;
    }

    /**
     * 暂时锁定用户的资产
     *
     * @param userId 用户的id
     * @param coinId 币种的id
     * @param mum 锁定的金额
     * @param type 资金流水的类型
     * @param orderId 订单的Id
     * @param fee
     */
    @Override
    public void lockUserAmount(Long userId, Long coinId, BigDecimal mum, String type, Long orderId, BigDecimal fee) {
        Account account = getOne(new LambdaQueryWrapper<Account>().eq(Account::getUserId, userId).eq(Account::getCoinId, coinId));
        if(account == null){
            throw new IllegalArgumentException("您输入的资产类型不存在");
        }
        BigDecimal balanceAmount = account.getBalanceAmount();
        if(balanceAmount.compareTo(mum) < 0){
            // 检查库存的操作
            throw new IllegalArgumentException("账号的资金不足");
        }
        account.setBalanceAmount(balanceAmount.subtract(mum));
        account.setFreezeAmount(account.getFreezeAmount().add(mum));
        boolean updateById = updateById(account);
        if(updateById){
            // 增加流水记录
            AccountDetail accountDetail = new AccountDetail(null, userId, coinId, account.getId(), account.getId(), // 如果该订单时邀请奖励,有我们的ref 的account ,否则,值和account 是一样的
                    orderId, (byte) 2, type, mum, fee, "用户提现", null, null, null);
            accountDetailService.save(accountDetail);
        }
    }

    /**
     * 计算用户的总的资产
     *
     * @param userId
     * @return
     */
    @Override
    public UserTotalAccountVo getUserTotalAccount(Long userId) {
        // 计算总资产
        UserTotalAccountVo userTotalAccountVo = new UserTotalAccountVo();
        BigDecimal basicCoin2CnyRate = BigDecimal.ONE; // 汇率
        BigDecimal basicCoin = BigDecimal.ZERO; // 平台计算币的基币
        List<AccountVo> assertList = new ArrayList<AccountVo>();
        // 用户的总资产位于Account 里面
        List<Account> accounts = list(new LambdaQueryWrapper<Account>()
                .eq(Account::getUserId, userId)
        );
        if (CollectionUtils.isEmpty(accounts)) {
            userTotalAccountVo.setAssertList(assertList);
            userTotalAccountVo.setAmountUs(BigDecimal.ZERO);
            userTotalAccountVo.setAmount(BigDecimal.ZERO);
            return userTotalAccountVo; //
        }
        AccountVoMappers mappers = AccountVoMappers.INSTANCE;
        // 获取所有的币种
        for (Account account : accounts) {
            AccountVo accountVo = mappers.toConvertVo(account);
            Long coinId = account.getCoinId();
            Coin coin = coinService.getById(coinId);
            if (coin == null || coin.getStatus() != (byte) 1) {
                continue;
            }
            // 设置币的信息
            accountVo.setCoinName(coin.getName());
            accountVo.setCoinImgUrl(coin.getImg());
            accountVo.setCoinType(coin.getType());
            accountVo.setWithdrawFlag(coin.getWithdrawFlag());
            accountVo.setRechargeFlag(coin.getRechargeFlag());
            accountVo.setFeeRate(BigDecimal.valueOf(coin.getRate()));
            accountVo.setMinFeeNum(coin.getMinFeeNum());

            assertList.add(accountVo);
            // 计算总的账面余额 //
            BigDecimal volume = accountVo.getBalanceAmount().add(accountVo.getFreezeAmount());
            accountVo.setCarryingAmount(volume); // 总的账面余额
            // 将该币和我们系统统计币使用的基币转化
            BigDecimal currentPrice = getCurrentCoinPrice(coinId);

            BigDecimal total = volume.multiply(currentPrice);
            basicCoin = basicCoin.add(total); // 将该子资产添加到我们的总资产里面
        }
        userTotalAccountVo.setAmount(basicCoin.multiply(basicCoin2CnyRate).setScale(8, RoundingMode.HALF_UP)); // 总的人民币
        userTotalAccountVo.setAmountUs(basicCoin); // 总的平台计算的币种(基础币)
        userTotalAccountVo.setAmountUsUnit("GCN");
        userTotalAccountVo.setAssertList(assertList);
        return userTotalAccountVo;
    }

    /**
     * 获取当前币的价格
     * 使用我们的基币兑换该币的价格
     *
     * @param coinId
     * @return
     */
    private BigDecimal getCurrentCoinPrice(Long coinId) {
        // 1 查询我们的基础币是什么?
        Config configBasicCoin = configService.getConfigByCode("PLATFORM_COIN_ID"); // 基础币
        if (configBasicCoin == null) {
            throw new IllegalArgumentException("请配置基础币后使用");
        }
        Long basicCoinId = Long.valueOf(configBasicCoin.getValue());
        if (coinId.equals(basicCoinId)) { // 该币就是基础币
            return BigDecimal.ONE;
        }
        // 不等于,我们需要查询交易市场   ,使用基础币作为我们报价货币,使用报价货币的的金额 来计算我们的当前币的价格
        MarketDto market = marketServiceFeign.findByCoinId(basicCoinId, coinId);
        if (market != null) { // 存在交易对
            return market.getOpenPrice();
        } else {
            // 该交易对不存在?
            log.error("不存在当前币和平台币兑换的市场,请后台人员及时添加");
            return BigDecimal.ZERO;//TODO
        }
    }

    /**
     * 统计用户交易对的资产
     *
     * @param symbol 交易对的Symbol
     * @param userId 用户的Id
     * @return
     */
    @Override
    public SymbolAssetVo getSymbolAssert(String symbol, Long userId) {
        /**
         * 远程调用获取市场
         */
        MarketDto marketDto = marketServiceFeign.findBySymbol(symbol);
        SymbolAssetVo symbolAssetVo = new SymbolAssetVo();
        // 查询报价货币
        @NotNull Long buyCoinId = marketDto.getBuyCoinId(); // 报价货币的Id
        Account buyCoinAccount = getCoinAccount(buyCoinId, userId);
        symbolAssetVo.setBuyAmount(buyCoinAccount.getBalanceAmount());
        symbolAssetVo.setBuyLockAmount(buyCoinAccount.getFreezeAmount());
        // 市场里面配置的值
        symbolAssetVo.setBuyFeeRate(marketDto.getFeeBuy());
        Coin buyCoin = coinService.getById(buyCoinId);
        symbolAssetVo.setBuyUnit(buyCoin.getName());
        // 查询基础汇报
        @NotBlank Long sellCoinId = marketDto.getSellCoinId();
        Account coinAccount = getCoinAccount(sellCoinId, userId);
        symbolAssetVo.setSellAmount(coinAccount.getBalanceAmount());
        symbolAssetVo.setSellLockAmount(coinAccount.getFreezeAmount());
        // 市场里面配置的值
        symbolAssetVo.setSellFeeRate(marketDto.getFeeSell());
        Coin sellCoin = coinService.getById(sellCoinId);
        symbolAssetVo.setSellUnit(sellCoin.getName());

        return symbolAssetVo;
    }

    /**
     * 获取用户的某种币的资产
     *
     * @param coinId
     * @param userId
     * @return
     */
    private Account getCoinAccount(Long coinId, Long userId) {

        return getOne(new LambdaQueryWrapper<Account>()
                .eq(Account::getCoinId, coinId)
                .eq(Account::getUserId, userId)
                .eq(Account::getStatus, 1)
        );
    }

    /**
     * 用户资金的划转
     *
     * @param adminId
     * @param userId
     * @param coinId
     * @param num
     * @param fee
     * @return
     */
    @Override
    public Boolean transferAccountAmount(Long adminId, Long userId, Long coinId, Long orderNum, BigDecimal num, BigDecimal fee, String remark, String businessType, Byte direction) {
        Account coinAccount = getCoinAccount(coinId, userId);
        if (coinAccount == null) {
            throw new IllegalArgumentException("用户当前的该币种的余额不存在");
        }
        // 增加一条流水记录
        AccountDetail accountDetail = new AccountDetail();
        accountDetail.setCoinId(coinId);
        accountDetail.setUserId(userId);
        accountDetail.setAmount(num);
        accountDetail.setFee(fee);
        accountDetail.setOrderId(orderNum);
        accountDetail.setAccountId(coinAccount.getId());
        accountDetail.setRefAccountId(coinAccount.getId());
        accountDetail.setRemark(remark);
        accountDetail.setBusinessType(businessType);
        accountDetail.setDirection(direction);
        accountDetail.setCreated(new Date());
        boolean save = accountDetailService.save(accountDetail);
        if (save) { // 用户余额的增加
            coinAccount.setBalanceAmount(coinAccount.getBalanceAmount().add(num));
            boolean updateById = updateById(coinAccount);
            return updateById;
        }
        return save;
    }

    /**
     * 给用户扣减钱
     *
     * @param adminId      操作的人
     * @param userId       用户的id
     * @param coinId       币种的id
     * @param orderNum     订单的编号
     * @param num          扣减的余额
     * @param fee          费用
     * @param remark       备注
     * @param businessType 业务类型
     * @param direction    方向
     * @return
     */
    @Override
    public Boolean decreaseAccountAmount(Long adminId, Long userId, Long coinId, Long orderNum, BigDecimal num, BigDecimal fee, String remark, String businessType, byte direction) {
        Account coinAccount = getCoinAccount(coinId, userId);
        if (coinAccount == null) {
            throw new IllegalArgumentException("账户不存在");
        }
        AccountDetail accountDetail = new AccountDetail();
        accountDetail.setUserId(userId);
        accountDetail.setCoinId(coinId);
        accountDetail.setAmount(num);
        accountDetail.setFee(fee);
        accountDetail.setAccountId(coinAccount.getId());
        accountDetail.setRefAccountId(coinAccount.getId());
        accountDetail.setRemark(remark);
        accountDetail.setBusinessType(businessType);
        accountDetail.setDirection(direction);
        boolean save = accountDetailService.save(accountDetail);
        if (save) { // 新增了流水记录
            BigDecimal balanceAmount = coinAccount.getBalanceAmount();
            BigDecimal result = balanceAmount.add(num.multiply(BigDecimal.valueOf(-1)));
            if (result.compareTo(BigDecimal.ONE) > 0) {
                coinAccount.setBalanceAmount(result);
                return updateById(coinAccount);
            } else {
                throw new IllegalArgumentException("余额不足");
            }
        }
        return false;
    }

    @Override
    public void transferBuyAmount(Long fromUserId, Long toUserId, Long coinId, BigDecimal amount, String businessType, Long orderId) {
        Account fromAccount = getCoinAccount(coinId, fromUserId);
        if (fromAccount == null) {
            log.error("资金划转-资金账户异常，userId:{}, coinId:{}", fromUserId, coinId);
            throw new IllegalArgumentException("资金账户异常");
        } else {
            Account toAccount = getCoinAccount(toUserId, coinId);
            if (toAccount == null) {
                throw new IllegalArgumentException("资金账户异常");
            } else {
                boolean count1 = decreaseAmount(fromAccount, amount);
                boolean count2 = addAmount(toAccount, amount);
                if (count1 && count2) {
                    List<AccountDetail> accountDetails = new ArrayList(2);
                    AccountDetail fromAccountDetail = new AccountDetail(fromUserId, coinId, fromAccount.getId(), toAccount.getId(), orderId, 2, businessType, amount, BigDecimal.ZERO, businessType);
                    AccountDetail toAccountDetail = new AccountDetail(toUserId, coinId, toAccount.getId(), fromAccount.getId(), orderId, 1, businessType, amount, BigDecimal.ZERO, businessType);
                    accountDetails.add(fromAccountDetail);
                    accountDetails.add(toAccountDetail);

                    accountDetails.addAll(accountDetails);
                } else {
                    throw new RuntimeException("资金划转失败");
                }
            }
        }
    }

    private boolean addAmount(Account account, BigDecimal amount) {
        account.setBalanceAmount(account.getBalanceAmount().add(amount));
        return updateById(account);
    }

    private boolean decreaseAmount(Account account, BigDecimal amount) {
        account.setBalanceAmount(account.getBalanceAmount().subtract(amount));
        return updateById(account);
    }

    @Override
    public void transferSellAmount(Long fromUserId, Long toUserId, Long coinId, BigDecimal amount, String businessType, Long orderId) {
        Account fromAccount = getCoinAccount(coinId, fromUserId);
        if (fromAccount == null) {
            log.error("资金划转-资金账户异常，userId:{}, coinId:{}", fromUserId, coinId);
            throw new IllegalArgumentException("资金账户异常");
        } else {
            Account toAccount = getCoinAccount(toUserId, coinId);
            if (toAccount == null) {
                throw new IllegalArgumentException("资金账户异常");
            } else {
                boolean count1 = addAmount(fromAccount, amount);
                boolean count2 = decreaseAmount(toAccount, amount);
                if (count1 && count2) {
                    List<AccountDetail> accountDetails = new ArrayList(2);
                    AccountDetail fromAccountDetail = new AccountDetail(fromUserId, coinId, fromAccount.getId(), toAccount.getId(), orderId, 2, businessType, amount, BigDecimal.ZERO, businessType);
                    AccountDetail toAccountDetail = new AccountDetail(toUserId, coinId, toAccount.getId(), fromAccount.getId(), orderId, 1, businessType, amount, BigDecimal.ZERO, businessType);
                    accountDetails.add(fromAccountDetail);
                    accountDetails.add(toAccountDetail);

                    accountDetails.addAll(accountDetails);
                } else {
                    throw new RuntimeException("资金划转失败");
                }
            }
        }
    }

}
