import request from "./request";
let qs = require('qs');
import md5 from 'js-md5';

export const financeApi = {
    /**
   * 获取银行卡列表
   */
  serverUserBank(token) {
    return request({
      url : '/user/userBanks/current',
      method : 'get',
      headers:{
        'Authorization': token,
      },
    })
  },

    /**
   * 获取cny充值记录
   */
  serverSearchPaymentList(size, current, status, token) {
    if(status == 4){
      status = null ;
    }
    return request({
      url : '/finance/cashRecharges/user/records',
      method : 'get',
      headers:{
        'Authorization': token,
      },
      params: {
        'size': size ,
        'current': current ,
        'status': status 
      },
    });
  },

  /**
  * 获取用户资产
  */
  getUserAssets(data, token) {
    let url = '/finance/account/asset';
    if(data) {
      url = '?'+qs.stringify(data)
    }
    return request({
      url: url,
      method: 'get',
      headers:{
        'Authorization': token,
      },
    })
  },

  /**
   * 获取cny提现记录
   */
  serverSearchWithdrawalsList(size, current, status, token) {
    if(status==4){
      status = null ;
    }
    return request({
      url : '/finance/cashWithdrawals/user/records',
      method : 'get',
      headers:{
        'Authorization': token,
      },
      params: {
        'size': size ,
        'current': current ,
        'status': status 
      },
    })
  },

  getCoinAsset (coinName, token) {
    return request({
      url : `/finance/account/${coinName}`,
      method : 'get',
      headers:{
        'Authorization': token,
      },
    })
  },

   /**
    * 卖出
    *
    * coinId 卖出币种
    * mum 提现金额
    * num 卖出数量
    * payPassword 支付密码
    * validateCode 验证码
   */
   serverCreateWithdrawals(coinId, mum, num, payPassword, validateCode, token) {
     let data = {
       coinId,
       mum,
       num,
       payPassword : md5(payPassword),
       validateCode
     };
     return request({
       url : '/finance/cashWithdrawals/sell',
       method : 'post',
       headers: {
         'Authorization': token,
       },
       data,
     });
  },
    /**
   * 获取充值备注信息
   */
  serverRecharge(coinId, mum, num, token) {
    let data = {
      coinId,
      mum,
      num,
    };
    return request({
      url : '/finance/cashRecharges/buy',
      method : 'post',
      headers: {
        'Authorization': token,
      },
      data,
    });
  },

  serverAccount(token) {
    return request({
      url : '/finance/account/total',
      method : 'get',
      headers: {
        'Authorization': token,
      },
    })
  },

    /**
   * 添加银行卡
   * bankCard 银行开号
   * bankName 开户银行
   * branchName 支行
   * city 开户行所在城市
   * name 开户名
   * address 银行地址
   * privince 开户行所在省
   */
  serverCreateUserBank(id, realName, remark, bank, bankCard, payPassword, token) {
    let data  = {
      id : id,
      realName   : realName,
      remark     : remark,
      bank       : bank,
/*      bankProv   : bankProv,
      bankCity   : bankCity,
      bankAddr   : bankAddr,*/
      bankCard   : bankCard,
      payPassword: md5(payPassword),
    }
    return request({
      url : '/user/userBanks/bind',
      method : 'post',
      headers: {
        'Authorization': token,
      },
      data,
    })
  },

  // 充值钱包地址
  serverRechargeAddress(coidId, token) {
    return request({
      url : '/user/userAddress/getCoinAddress/'+coidId,
      method : 'get',
      headers: {
        'Authorization': token,
      },
    })
  },

  /**
 * 钱包币列表
 */
  getWalletCoinList() {
    return request({
      url : '/v2/s/coin/trade/wallet',
      method : 'get',
    });
  },

  // 基础币列表
  getBaseCoinList() {
    return request({
      url : '/finance/coins/all?status=1',
      method : 'get',
    })
  },

    /**
   * 钱包币地址列表
   */
  walletList(coinId, token) {
    return request({
      url : '/user/userWallets/getCoinAddress/'+coinId,
      method : 'get',
      headers: {
        'Authorization': token,
      },
    })
  },

  /**
   * 增加钱包地址
   */
  serverAddWallet(coinId, name, addr, payPassword, token) {
    let data = {
      coinId,
      name,
      addr,
      payPassword : md5(payPassword)
    }
    return request({
      url : '/user/userWallets',
      method : 'post',
      headers: {
        'Authorization': token,
      },
      data,
    });
  },

  //资产管理 充值记录
  serverInWalletRecord(size, current, coinId, token) {
    if(coinId == 0){
      coinId = null ;
    }
    let data = {
      size,
      current,
      coinId
    }
    return request({
      url : '/finance/coinRecharges/user/record?'+qs.stringify(data),
      method : 'get',
      headers: {
        'Authorization': token,
      },
    });
  },

  //资产管理 提现记录
  serverOutWalletRecord(size, current, coinId, token) {
    if(coinId==0){
      coinId = null ;
    }
    let data = {
      size,
      current,
      coinId
    }
    return request({
      url : '/finance/coinWithdraws/user/record?'+qs.stringify(data),
      method : 'get',
      headers: {
        'Authorization': token,
      },
    });
  },

  /**
  * 删除钱包地址
  */
  serverdeleteWalletAddress(addressId, payPassword, token) {
    return request({
      url : '/user/userWallets/deleteAddress',
      method : 'post',
      headers: {
        'Authorization': token,
      },
      params:{
        addressId: addressId ,
        payPassword: md5(payPassword)
      },
    });
  },


  // 提现
  serverWithdraw (addressId, amount, coinId, payPassword, verifyCode, token) {
    let data = {
      addressId,
      amount,
      coinId,
      payPassword : md5(payPassword),
      verifyCode
    }
    return request({
      url : '/v2/s/withdraw',
      method : 'post',
      headers: {
        'Authorization': token,
      },
      data,
    });
  },
  // 获取矿池资产  /reward/info
  /**
   user_id
   amount 总冻结
   thawed 已经解冻量
   freeze 冻结奖励
   can_defrost 可解冻奖励
   */
  getRewardInfo() {
    return request({
      url : '/v2/s/reward/info',
      method : 'get'
    });
  },
  // 解冻资金
  unfreezeReward() {
    return request({
      url : '/v2/s/reward/unfreeze',
      method : 'post'
    });
  }
}
