<template>
  <div class="ucenter-wrap ctoc" v-wechat-title="$t('m.navheader.c2c')">
    <NavHeader activeTab="8"></NavHeader>
    <div class="ucenter-content">
      <div class="ucenter-row">
        <div class="ucenter-main no-sidebar">
          <div class="ucenter-header">
            <h3 class="title">
              {{$t('m.navheader.c2c')}}
            </h3>
          </div>
          <div class="ucenter-body">
            <el-row :gutter="30">
              <el-col :span="12">
                <div class="wallet">
                  <div class="wallet-image">
                    <img src="/static/img/finance/bankcard.png" alt="">
                  </div>
                  <div class="wallet-balance">
                    <p>{{$t('m.c2c.usdtAvailable')}} <span>{{feeRateAndBank.balanceAmount}}</span> GCN</p>
                    <p>{{$t('m.c2c.usdtFreeze')}} <span>{{feeRateAndBank.freezeAmount}}</span> GCN</p>
                  </div>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="wallet">
                  <div class="bankcard">
                    <div class="wallet-image">
                      <img src="/static/img/finance/zhaoshang.png" width="80"/>
                    </div>
                    <div class="wallet-card-info" v-if="bankData">
                      <p>{{bankData.bank}}</p>
                      <p>{{bankData.realName}}</p>
                      <p :title="bankData.bankCard">{{_bankcardFormat(bankData.bankCard)}}</p>
                    </div>
                  </div>
                  <div class="add-bankcard">
                    <a href="javascript:;" @click="handleAddBankCard">+ {{addOrModifyBankcard　== ''　?
                      $t('m.c2c.addBankCard') : $t('m.c2c.updateBankCrad')}}</a>
                  </div>
                </div>
              </el-col>
            </el-row>

            <el-row :gutter="40" class="exchange">
              <el-col :span="12">
                <el-form ref="form" :model="rechargeForm" :rules="rechargeRules" label-position="top">
                  <el-form-item>
                    <div slot="label" class="label-row">
                      <span>{{$t('m.c2c.buyCoin')}}</span>
                      <span></span>
                    </div>
                    <el-input :placeholder="$t('m.c2c.buyPrice')" :disabled="true">
                      <div slot="suffix" class="price green">{{feeRateAndBank.buyRate}}</div>
                    </el-input>
                  </el-form-item>

                  <el-form-item prop="rechargeNum">
                    <div slot="label" class="label-row">
                      <span>{{$t('m.c2c.buyAmount')}}</span>
                      <span></span>
                    </div>
                    <el-input type="text"
                              :placeholder="$t('m.c2c.pleaseEnterBuyAmount')"
                              @keyup.native="amountEnterLimit(rechargeForm.rechargeNum,$event,0)"
                              v-model="rechargeForm.rechargeNum"
                              :disabled="!feeRateAndBank"></el-input>
                    <div class="sub-title">{{$t('m.c2c.needCNY')}} {{getRechargeAmount}}</div>
                  </el-form-item>
                  <el-button type="primary" @click="handleBuySellBtcx(0)">
                    {{$t('m.c2c.buy')}}
                  </el-button>
                </el-form>
              </el-col>
              <el-col :span="12">
                <el-form ref="form" :model="sellBtcxForm" :rules="sellBtcxRules" label-position="top">
                  <el-form-item>
                    <div slot="label" class="label-row">
                      <span>{{$t('m.c2c.sellCoin')}}</span>
                      <span></span>
                    </div>
                    <el-input :placeholder="$t('m.c2c.sellPrice')" :disabled="true">
                      <div slot="suffix" class="price red">{{feeRateAndBank.sellRate}}</div>
                    </el-input>
                  </el-form-item>
                  <el-form-item prop="num">
                    <div slot="label" class="label-row">
                      <span>{{$t('m.c2c.sellAmount')}}</span>
                      <span></span>
                    </div>
                    <el-input type="text"
                              :placeholder="$t('m.c2c.pleaseEnterSellAmount')"
                              @keyup.native="amountEnterLimit(sellBtcxForm.num,$event,1)"
                              v-model="sellBtcxForm.num"
                              :disabled="!feeRateAndBank">
                    </el-input>
                    <div class="sub-title">{{$t('m.c2c.getCNY')}} {{getWithdrawAmount}}</div>
                  </el-form-item>
                  <el-button class="btn-btcx-sell" @click="handleSellBtcx(1)">
                    {{$t('m.c2c.sell')}}
                  </el-button>
                </el-form>
              </el-col>
            </el-row>

            <div class="notice-panel" v-if="lang === 'zh-CN'">
              <p class="notice-title">温馨提示</p>
              <p>尊敬的用户：为了您的购买可以快速到账，请在交易前仔细阅读以下注意事项：</p>
              <ul>
                <li class="red">在平台发起充值申请后，请使用平台公布的汇款方式进行汇款。</li>
                <li>商家处理时间9:00 - 21:00非处理时间的订单会在第二天9:00开始处理，接单后2小时内会完成交易，时间不会超过24小时。请在下单后30分钟内完成付款，否则会延时到账。</li>
                <!--<li class="red">请使用本人绑定的银行卡通过手机银行或网银进行汇款，其他任何方式汇款都会造成延时处理。</li>-->
                <li>如果你的交易24小时内没有处理，请联系在线客服登记。会有专人24小时内与您联系，请保持手机畅通。</li>
                <li>由于咨询量过大，24小时内未处理的交易订单咨询，在线客服无法提供帮助。敬请谅解！</li>
                <!--<li class="red">禁止微信支付宝转账</li>-->
                <li class="red">禁止微信支付宝转账，请使用本人绑定的银行卡通过手机银行或网银进行汇款，同时汇款银行留言栏备注本订单参考号、参考号、参考号（重要的事情说三遍），其它任何方式汇款都将在72小时内处理！</li>
              </ul>
            </div>
            <div class="notice-panel" v-else-if="lang === 'zh-HK'">
              <p class="notice-title">溫馨提示</p>
              <p>尊敬的用戶：為了您的購買可以快速到賬，請在交易前仔細閱讀以下註意事項：</p>
              <ul>
                <li class="red">在平臺發起充值申請後，請使用平臺公布的匯款方式進行匯款。</li>
                <li>商家處理時間9:00 - 21:00非處理時間的訂單會在第二天9:00開始處理，接單後2小時內會完成交易，時間不會超過24小時。請在下單後30分鐘內完成付款，否則會延時到賬。</li>
                <!--<li class="red">請使用本人綁定的銀行卡通過手機銀行或網銀進行匯款，其他任何方式匯款都會造成延時處理。</li>-->
                <li>如果妳的交易24小時內沒有處理，請聯系在線客服登記。會有專人24小時內與您聯系，請保持手機暢通。</li>
                <li>由於咨詢量過大，24小時內未處理的交易訂單咨詢，在線客服無法提供幫助。敬請諒解！</li>
                <!--<li class="red">禁止微信支付寶轉賬!</li>-->
                <li class="red">禁止微信支付寶轉帳，請使用本人綁定的銀行卡通過手機銀行或網銀進行匯款，同時匯款銀行留言欄備註本訂單參攷號、參攷號、參攷號（重要的事情說三遍），其它任何管道匯款都將在72小時內處理！</li>
              </ul>
            </div>
            <div class="notice-panel" v-else>
              <p class="notice-title">Warm reminding</p>
              <p>Dear customer: for your purchase, you can get the bill quickly. Please read the following points
                carefully before the transaction:</p>
              <ul>
                <li class="red">After recharging the platform, please use the remittance method announced by the
                  platform to make remittance.
                </li>
                <li>Business processing time 9:00 - 21:00 non processing time orders will be processed in second days,
                  after the receipt of the order will complete the transaction within 2 hours, time will not exceed 24
                  hours. Please complete the payment within 30 minutes after placing the order, otherwise it will be
                  delayed.
                </li>
                <!--<li class="red">Please use the bank card that I bind to make remittance through mobile banking or online-->
                  <!--banking. Any other way of remittance will cause delay processing.-->
                <!--</li>-->
                <li>If your transaction is not processed within 24 hours, please contact online customer service
                  registration. A special person will contact you within 24 hours. Please keep your mobile phone
                  unblocked.
                </li>
                <li>Due to excessive volume of consultation and unhandled orders in 24 hours, online customer service
                  can not help. Please understand！
                </li>
                <!--<li class="red">WeChat Alipay is prohibited from transferring money</li>-->
                <li class= "red" > prohibit WeChat Alipay transfer, please use my bound bank card through mobile bank or net silver for remittance, and remittance bank message column to note this order reference number, reference number, reference number (important things say three times), any other way of remittance will be handled within 72 hours! </li>
              </ul>
            </div>
            <div class="table-wrap">
              <div class="select-container">
                <el-select v-model="statusValue" @change="onStateChange" size="small">
                  <el-option
                    v-for="item in statusOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>

              </div>
              <el-tabs v-model="activeName" @tab-click="handleTabClick">
                <el-tab-pane :label="$t('m.c2c.buyingRecodes')" name="recharge">
                  <!--C2C买入记录-->
                  <div class="cny-recharge-withdraw-record">

                    <el-table
                      :data="cnyRechargeRecord"
                      header-cell-class-name="table-header"
                    >
                      <el-table-column
                        prop="tradeno"
                        :label="$t('m.c2c.rechangeNo')"
                        align="center"
                        width="220"
                      >
                      </el-table-column>
                      <el-table-column
                        prop="created"
                        :label="$t('m.c2c.rechangeTime')"
                        width="220"
                        align="center"
                      >
                      </el-table-column>
                      <el-table-column
                        prop="type"
                        :label="$t('m.c2c.rechangeMethod')"
                        align="center"
                        :formatter="formatRechargeType"
                      >
                        <template slot-scope="scope">
                          {{$t(`m.c2c.rechargeType.${scope.row.type}`)}}
                        </template>
                      </el-table-column>
                      <el-table-column
                        prop="num"
                        :label="$t('m.c2c.actualAccount1')"
                        align="center"
                        width="180"

                      >
                      </el-table-column>
                      <el-table-column
                        prop="mum"
                        :label="$t('m.c2c.rechangeAmount')"
                        align="center"
                        width="180"

                      >
                      </el-table-column>
                      <el-table-column
                        prop="status"
                        :label="$t('m.c2c.status')"
                        align="center"
                      >
                        <template slot-scope="scope">
                          {{$t(`m.c2c.rechargeMap[${scope.row.status}]`)}}
                        </template>
                      </el-table-column>
                      <el-table-column
                        prop="status"
                        :label="$t('m.operate')"
                        align="center"

                      >
                        <template slot-scope="scope">
                          <el-button @click="handleRechargeRecord(scope.row)"
                                     :disabled="scope.row.status===3" size="mini">
                            {{$t('m.c2c.offlinePayment')}}
                          </el-button>

                        </template>
                      </el-table-column>


                    </el-table>

                    <div class="pagination-container">
                      <el-pagination
                        background
                        layout="prev, pager, next"
                        :total="totalRecords"
                        :page-size="rows"
                        @current-change="handlePageChange"
                      >
                      </el-pagination>
                    </div>

                  </div>
                </el-tab-pane>

                <el-tab-pane :label="$t('m.c2c.sellingRecodes')" name="withdraw">
                  <!--C2C卖出记录-->
                  <div class="cny-recharge-withdraw-record">
                    <el-table
                      :data="cnyWithdrawRecord"
                      header-cell-class-name="table-header"
                    >
                      <el-table-column
                        prop="created"
                        :label="$t('m.c2c.withdrawalTime')"
                        align="center"
                      >
                      </el-table-column>
                      <!--<el-table-column-->
                      <!--prop="type"-->
                      <!--label="充值方式"-->
                      <!--align="center"-->
                      <!--:formattformatRechargeTypeType"-->
                      <!--&gt;-->
                      <!--</el-table-column>-->
                      <el-table-column
                        prop="num"
                        :label="$t('m.c2c.withdrawalAmount')"
                        align="center"
                      >
                      </el-table-column>
                      <el-table-column
                        prop="fee"
                        :label="$t('m.c2c.fee')"
                        align="center"
                      >
                      </el-table-column>
                      <el-table-column
                        prop="mum"
                        :label="$t('m.c2c.actualAccount')"
                        align="center"
                      >
                      </el-table-column>
                      <el-table-column
                        prop="bankCard"
                        :label="$t('m.c2c.bankCardNumber')"
                        align="center"
                        :formatter="formatBankcard"
                      >
                      </el-table-column>
                      <el-table-column
                        prop="status"
                        :label="$t('m.c2c.status')"
                        align="center"


                      >
                        <template slot-scope="scope">
                          {{$t(`m.c2c.withdrawMap[${scope.row.status}]`)}}
                        </template>
                      </el-table-column>


                    </el-table>

                    <div class="pagination-container">
                      <el-pagination
                        background
                        layout="prev, pager, next"
                        :total="totalRecords"
                        :page-size="rows"
                        @current-change="handlePageChange"
                      >
                      </el-pagination>
                    </div>

                  </div>
                </el-tab-pane>
              </el-tabs>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="ucenter-footer">
      <mFooter class="footer-bar"></mFooter>
    </div>

    <!--添加银行卡弹窗-->
    <el-dialog
      :title="$t('m.c2c.addBankCard')"
      :visible.sync="dialogVisible"
      class="ucenter-dialog"
      width="530px">

      <el-alert
        :closable="false"
        show-icon
        :title="$t('m.c2c.addCardTips')"
        type="warning">
      </el-alert>

      <div style="padding: 20px;">
        <el-form :model="bankcardForm" :rules="bankcardRules" ref="bankcardForm" class="dialog-form" label-width="80px">
          <el-form-item :label="$t('m.c2c.accountName')" prop="name">
            <el-input v-model="bankcardForm.name" :placeholder="$t('m.c2c.accountName')" class="dialog-input"
                      auto-complete="off" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item :label="$t('m.c2c.remarkName')" prop="remark">
            <el-input v-model="bankcardForm.remark" :placeholder="$t('m.c2c.remarkName')" class="dialog-input"
                      auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item :label="$t('m.c2c.accountOpeningBank')" prop="bank">
            <el-select v-model="bankcardForm.bank" :placeholder="$t('m.c2c.accountOpeningBank')" class="dialog-input"
                       auto-complete="off">
              <el-option
                v-for="item in bankListOptionFixed"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item :label="$t('m.c2c.bankCardNumber')" prop="bankcard">
            <el-input v-model="bankcardForm.bankcard" :placeholder="$t('m.c2c.enterBankCardTips')" class="dialog-input"
                      auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item :label="$t('m.c2c.confirmCardNumber')" prop="bankcardConfirm">
            <el-input v-model="bankcardForm.bankcardConfirm" :placeholder="$t('m.c2c.confirmCardNumber')"
                      class="dialog-input"
                      auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item :label="$t('m.c2c.tradePassword')" prop="payPassword">
            <input type="text" style="display: none">
            <el-input v-model="bankcardForm.payPassword" :placeholder="$t('m.c2c.tradePassword')" class="dialog-input"
                      type="password"
                      name="tradePassword"
                      auto-complete="tradePassword"></el-input>
            <input type="password" style="display: none">
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('bankcardForm')" class="addbankcard-btn">{{$t('m.add')}}
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>

    <!--买入弹窗-->
    <el-dialog
      :title="$t('m.c2c.remittanceOrder')"
      :visible.sync="buyBtcxDialogVisible"
      width="500px">
      <el-alert
        :title="$t('m.c2c.remittanceNotice')"
        show-icon
        type="warning"
        :closable="false">
      </el-alert>
      <div style="padding:30px;line-height:30px">
        <!--<el-row>
          <el-col :span="24">{{$t('m.c2c.payeeTips')}}</el-col>
        </el-row>-->
        <el-row>
          <el-col :span="8">{{$t('m.c2c.payeeAccountName')}}</el-col>
          <el-col :span="16">{{rechargeInfo.name}}</el-col>
        </el-row>
        <el-row>
          <el-col :span="8">{{$t('m.c2c.payeeBank')}}</el-col>
          <el-col :span="16">{{rechargeInfo.bankName}}</el-col>
        </el-row>
        <el-row>
          <el-col :span="8">{{$t('m.c2c.payeeAccountNumber')}}</el-col>
          <el-col :span="16" style="font-weight:bold">{{rechargeInfo.bankCard}}</el-col>
        </el-row>
        <el-row>
          <el-col :span="8">{{$t('m.c2c.transferAmount')}}</el-col>
          <el-col :span="16">
            <span class="remark-red">￥{{rechargeRealNum}}</span>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">{{$t('m.c2c.remarkContent')}}</el-col>
          <el-col :span="16">
            <span class="remark-red">{{rechargeRemark}}({{$t('m.c2c.remarkMust')}})</span>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">{{$t('m.c2c.status')}}</el-col>
          <el-col :span="16">
            <span v-if="rechargeInfo.status != null">
              {{$t(`m.c2c.rechargeMap[${rechargeInfo.status}]`)}}
            </span>
          </el-col>
        </el-row>
        <el-row style="text-align: right;margin-top:15px">
          <el-button type="primary" @click="buyBtcxDialogVisible = false">{{$t('m.yes')}}</el-button>
        </el-row>
      </div>

    </el-dialog>
    <!--卖出弹窗-->
    <el-dialog
      :title="$t('m.c2c.sellOrder')"
      :visible.sync="sellBtcxDialogVisible"
      width="350px"
    >
      <div style="padding: 0 25px 30px 25px;margin-top: -15px">
        <div class="sell-input-group first">
          <label v-text="$t('m.userCenter.validationCode')"></label>
          <el-input :placeholder="$t('m.userCenter.plzValidationCode')" v-model="sellBtcxForm.validateCode"
                    :maxlength="6">
            <template slot="append">
              <validateCode ref="validate" :mobile="mobile" type="CASH_WITHDRAWS"
                            style="min-width: 80px;text-align:center" tag="span" :countryCode="this.countryCode"></validateCode>
            </template>
          </el-input>
        </div>
        <div class="sell-input-group">
          <label v-text="$t('m.c2c.tradePassword')"></label>
          <el-input :placeholder="$t('m.c2c.tradePassword')"
                    v-model="sellBtcxForm.payPassword"
                    type="password"
                    :maxlength="20"
                    auto-complete="off">
          </el-input>
        </div>
        <el-button @click="submitSellBtcxForm" type="primary" style="background-color:#7392ff">
          {{$t('m.yes')}}
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import Vue from 'vue'
  import ToggleButton from 'vue-js-toggle-button'
  import NavHeader from 'components/nav-header/nav-header'
  import mFooter from 'components/m-footer/m-footer'
  import VueClipboard from 'vue-clipboard2'
  import dom from 'common/js/dom'
  import {financeApi} from 'api/finance'
  import {util} from 'common/js/util'
  import validateCode from 'base/validate-code/validate-code'

  import {OK} from 'api/config'
  import VueAreaLinkage from 'vue-area-linkage';
  import financeHeader from '../finance/finance-header'
  import Vodal from 'vodal';
  import {mapGetters, mapState} from 'vuex'


  Vue.use(VueAreaLinkage)
  Vue.use(VueClipboard)
  Vue.use(ToggleButton)
  export default {
    data() {

      let checkRemark = (rule, value, callback) => {
        if (value === '') {
          callback(new Error(this.$t('m.c2c.remarkIptName')));
        } else {
          callback();
        }
      }

      let checkName = (rule, value, callback) => {
        if (value === '') {
          callback(new Error(this.$t('m.c2c.accountIptName')));
        } else {
          callback()
        }
      }

      let checkBank = (rule, value, callback) => {
        if (value === '') {
          callback(new Error(this.$t('m.c2c.selectBank')));
        } else {
          callback()
        }
      }

      let checkBankRegion = (rule, value, callback) => {
        if (value === '') {
          callback(new Error(this.$t('m.c2c.selectAccountBankAddress')));
        } else {
          callback()
        }
      }

      let checkBankDetailName = (rule, value, callback) => {
        if (value === '') {
          callback(new Error(this.$t('m.c2c.selectAccountBankName')));
        } else {
          callback()
        }
      }

      let checkBandCard = (rule, value, callback) => {
        if (value === '') {
          callback(new Error(this.$t('m.c2c.inputBankCrad')));
        } else {
          callback()
        }
      }

      let validatePayPassword = (rule, value, callback) => {
        if (value === '') {
          callback(new Error(this.$t('m.userCenter.plzFundPassword')));
        } else {
          callback()
        }
      }

      let validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error(this.$t('m.c2c.reEnterBankCrad')));
        } else if (value !== this.bankcardForm.bankcard) {
          callback(new Error(this.$t('m.c2c.twoReEnterBankCrad')));
        } else {
          callback();
        }
      };

      let validateRechargeNum = (rule, value, callback) => {
        if(!value){
          callback(new Error(this.$t('m.c2c.inputRechangeNumber')));
        } else if (Number(value) < 100) {
          callback(new Error(this.$t('m.c2c.rechangeMaxNumber')));
        } else {
          callback();
        }
      };

      let validateWithdrawNum = (rule, value, callback) => {
        if(!value){
          callback(new Error(this.$t('m.c2c.inputWithdrawNumber')));
        } else if (value < 100) {
          callback(new Error(this.$t('m.c2c.withdrawMinNumber')));
        } else {
          callback();
        }
      };

      let checkBankCard = (rule, value, callback) => {
        if (value === '') {
          callback(new Error(this.$t('m.c2c.selectBankCard')));
        } else {
          callback()
        }
      }

      let checkValidate = (rule, value, callback) => {
        if (value === '') {
          callback(new Error(this.$t('m.c2c.inputVCode')));
        } else {
          callback()
        }
      }

      let checkPayPassword = (rule, value, callback) => {
        if (value === '') {
          callback(new Error(this.$t('m.userCenter.plzFundPassword')));
        } else {
          callback()
        }
      }

      return {
        // 公司银行卡信息
        companyBankInfo: '',
        rechargeInfo: {},
        feeRateAndBank: {},
        addOrModifyBankcard: '',
        // 添加银行卡表单规则
        bankcardForm: {
          remark: '',
          name: '',
          bank: '',
          bankRegion: [],
          bankDetailName: '',
          bankcard: '',
          bankcardConfirm: '',
          payPassword: ''
        },
        bankcardRules: {
          remark: [
            {validator: checkRemark, trigger: 'blur'}
          ],
          name: [
            {validator: checkName, trigger: 'blur'}
          ],
          bank: [
            {validator: checkBank, trigger: 'blur'}
          ],
          bankRegion: [
            {validator: checkBankRegion, trigger: 'blur'}
          ],
          bankDetailName: [
            {validator: checkBankDetailName, trigger: 'blur'}
          ],
          bankcard: [
            {validator: checkBandCard, trigger: 'blur'}
          ],
          bankcardConfirm: [
            {validator: validatePass, trigger: 'blur'}
          ],
          payPassword: [
            {validator: validatePayPassword, trigger: 'blur'},
            {min: 8, max: 20, message: '长度在 8 到 20 位', trigger: 'blur'}
          ]
        },
        // 提现规则
        sellBtcxForm: {
          bankId: '',
          num: 0,
          // 验证码
          validateCode: '',
          payPassword: ''
        },
        sellBtcxRules: {
          bankId: [
            {validator: checkBankCard, trigger: 'blur'}
          ],
          num: [
            {validator: validateWithdrawNum, trigger: 'blur'}
          ],
          validateCode: [
            {validator: checkValidate, trigger: 'blur'}
          ],
          payPassword: [
            {validator: checkPayPassword, trigger: 'blur'},
            {min: 8, max: 20, message: '长度在 8 到 20 位', trigger: 'blur'}
          ]
        },
        rechargeForm: {
          rechargeNum: 0
        },
        rechargeRules: {
          rechargeNum: [
            {validator: validateRechargeNum, trigger: 'blur'}
          ],
        },

        needCny: 0,
        needUSDT: 0,

        // 用户手机号
        dialogVisible: false,
        rows: 5,
        page: 1,
        totalRecords: 0,
        currentTab: 0,
        cnyRechargeRecord: [],
        cnyWithdrawRecord: [],
        activeName: 'recharge',
        balanceAmount: 0,
        freezeAmount: 0,
        bankData: {},
        // dialog 区域 固定银行列表
        bankListOptionFixed: [{
          label: "中国银行",
          value: "中国银行"
        }, {
          label: "建设银行",
          value: "建设银行"
        }, {
          label: "工商银行",
          value: "工商银行"
        }, {
          label: "农业银行",
          value: "农业银行"
        }, {
          label: "交通银行",
          value: "交通银行"
        }, {
          label: "民生银行",
          value: "民生银行"
        }, {
          label: "招商银行",
          value: "招商银行"
        }, {
          label: "广发银行",
          value: "广发银行"
        }, {
          label: "浦发银行",
          value: "浦发银行"
        },
        // pcn 补充
          {
            label: "北京银行",
            value: "北京银行"
          },
          {
            label: "光大银行",
            value: "光大银行"
          },
          {
            label: "平安银行",
            value: "平安银行"
          },
          {
            label: "中国邮政",
            value: "中国邮政"
          },
          {
            label: "农村商业银行",
            value: "农村商业银行"
          },

        ],
//        提现银行列表
//         bankListOptionWithdraw: [],
        // 人民币充值
        rechargeNum: '',
        rechargeRemark: '',
        rechargeRealNum: '',
        statusOptions: [],
        // 充值记录status -1
        statusI18nMap: {
          'recharge': {
            'zh-CN': [
              {
                label: "全部",
                value: 4
              },
              {
                label: '待审核',
                value: 0
              }, {
                label: "审核通过",
                value: 1
              }, {
                label: "拒绝",
                value: 2
              }, {
                label: "充值成功",
                value: 3
              }
            ],
            'zh-HK': [
              {
                label: "全部",
                value: 4
              },
              {
                label: "待審核",
                value: 0
              }, {
                label: "審核通過",
                value: 1
              }, {
                label: "拒絕",
                value: 2
              }, {
                label: "充值成功",
                value: 3
              }
            ],
            'en-US': [
              {
                label: "All",
                value: 4
              },
              {
                label: "Wait for audit",
                value: 0
              }, {
                label: "Audited",
                value: 1
              }, {
                label: "Reject",
                value: 2
              }, {
                label: "Recharged",
                value: 3
              }
            ]
          },
          'withdraw': {
            'zh-CN': [
              {
                label: "全部",
                value: 4
              },
              {
                label: "待审核",
                value: 0
              }, {
                label: "审核通过",
                value: 1
              }, {
                label: "拒绝",
                value: 2
              }, {
                label: "提现成功",
                value: 3
              }
            ],
            'zh-HK': [
              {
                label: "全部",
                value: 4
              },
              {
                label: "待審核",
                value: 0
              }, {
                label: "審核通過",
                value: 1
              }, {
                label: "拒絕",
                value: 2
              }, {
                label: "提幣成功",
                value: 3
              }
            ],
            'en-US': [
              {
                label: "All",
                value: 4
              },
              {
                label: "Wait for audit",
                value: 0
              }, {
                label: "Audited",
                value: 1
              }, {
                label: "Reject",
                value: 2
              }, {
                label: "Withdrawn",
                value: 3
              }
            ]
          }
        },
        statusValue: 4,
//        支付弹窗
        buyBtcxDialogVisible: false,
        // 卖出弹窗
        sellBtcxDialogVisible: false,


//        rechargeDialog       : {
//          rechargeRemark : '',
//          rechargeRealNum: ''
//        }

      }
    },
    watch: {
      'lang' (n){
        this.statusOptions = this.statusI18nMap[this.activeName][n];
      }
    },
    created() {

    },
    mounted() {
      this.statusOptions = this.statusI18nMap[this.activeName][this.lang];
      console.log(this.statusI18nMap[this.activeName][this.lang])
      this._getUserAssets();
      this._serverUserBankList();
      this._serverSearchPaymentList();
    },
    computed: {
      ...mapGetters(['mobile', 'realName', 'token', 'isAuth', 'setPayPassword', 'lang','countryCode']),
      getRechargeAmount() {
        return (this.rechargeForm.rechargeNum * Number(this.feeRateAndBank.buyRate) || 0).toFixed(2);
      },
      getWithdrawAmount() {
        return (this.sellBtcxForm.num * Number(this.feeRateAndBank.sellRate) || 0).toFixed(2);
      }
    },
    methods   : {
      amountEnterLimit(value, event, type){
        if(typeof value === 'number') {
          value = value.toString();
        }
        let numMax = 8, numScale = 2;
        if(event.keyCode === 16 || event.keyCode === 32){
          value = value.replace(/[^\d+|\.{0,1}]/g,'');
        }
        if(/^\d+(?=\.{0,1}\d+$|\.{0,1}$|$)/.test(value)) {
          value = value.replace(/^0+/,'0');
        }else{
          value = value.replace(/[^\d+|\.{0,1}]/g,'');
        }
        // 如果存在小数点
        if(value.indexOf('.') !== -1) {
          let arr = value.split('.');
          if(arr.length > 2) {
            let lastStr = value.charAt(value.length-1);
            if(lastStr === '.') {
              value = value.slice(0,value.length-1);
            }
          }else{
            let tempArr = new Array(2);
            if(arr[0]) {
              let len = arr[0].length;
              if(len > numMax) {
                tempArr[0] = arr[0].slice(0, numMax);
              }else{
                tempArr[0] = arr[0];
              }
            }
            if(arr[1]) {
              let len = arr[1].length; // 小数位数
              if(len > numScale) {
                tempArr[1] = arr[1].slice(0,numScale);
              }else{
                tempArr[1] = arr[1];
              }
            }
            value = tempArr.join('.');
          }
        }else{
          let len = value.length;
          if(len > numMax) {
            value = value.slice(0, numMax);
          }
        }
        if(type === 1) {
          this.sellBtcxForm.num = value;
        }else{
          this.rechargeForm.rechargeNum = value;
        }
      },
      async _getUserAssets() {
        let res = await financeApi.getCoinAsset('GCN', this.token);
        this.feeRateAndBank = res.data;
        this.feeRateAndBank.buyRate = parseFloat(res.data.buyRate).toFixed(2);
        this.feeRateAndBank.sellRate = parseFloat(res.data.sellRate).toFixed(2);
      },
      //async _getC2CRate() {
      // let res = await  financeApi.getC2CRate()
      // this.feeRateAndBank = res.result;
      // this.companyBankInfo =
      // console.log("c2c费率", this.feeRateAndBank)

      //},
      // 获得充值备注
      handleBuySellBtcx(type) {
        if (Number(this.rechargeForm.rechargeNum) < 100) {
          this.$message({
            type: "error",
            message: this.$t('m.c2c.rechangeMaxNumber')
          })
          return
        }

        console.log("充值金额", this.getRechargeAmount);

        if (this.bankData.hasOwnProperty('bank')) {

          financeApi.serverRecharge(
            this.feeRateAndBank.coinId,
            this.getRechargeAmount,
            this.rechargeForm.rechargeNum,
            this.token
          ).then(res => {
            console.log("充值成功：");

            this.rechargeRemark = res.data.remark
            this.rechargeRealNum = res.data.amount

            this.rechargeInfo = res.data

            this.rechargeForm.rechargeNum = '0'

            this.buyBtcxAlert();
            //刷新充值记录
            this._serverSearchPaymentList()
          }).catch((res) => {

          });
        } else {
          this.handleAddBankCard()
        }
      },
      goToSeniorAuth() {
        this.$alert(this.$t('m.userCenter.plzSeniorAuth'), this.$t('m.prompt'), {
          confirmButtonText: this.$t('m.yes'),
          type: 'warning',
          callback: action => {
            this.$router.push("/usercenter/modify-id")
          }
        });
      },
      handleSellBtcx() {
        if (this.isAuth < 1) {
          this.goToSeniorAuth()
          return
        }
        let num = Number(this.sellBtcxForm.num);
        if (num < 100) {
          this.$message({
            type: "error",
            message: this.$t('m.c2c.sellMaxNumber')
          })
          return
        }
        if (num > this.feeRateAndBank.balanceAmount) {
          this.$message({
            type: "error",
            message: this.$t('m.amountError')
          })
          return false;
        }

        if (this.bankData.hasOwnProperty('bank')) {
          this.sellBtcxDialogVisible = true
          this.$nextTick(()=>{
            this.$refs.validate.reset();
          });
        } else {
          this.handleAddBankCard()
        }
      },

      handleAddBankCard() {
        if (this.isAuth == 0) {
          this.$confirm(
            this.$t('m.c2c.setRealName'),
            this.$t('m.prompt'),
            {
              confirmButtonText: this.$t('m.userCenter.goSetting'),
              cancelButtonText: this.$t('m.no'),
              type: 'warning'
            }
          ).then(() => {
            this.$router.push({
              path: "/usercenter/modify-id",
              query: {
                referrer: '/c2c'
              }
            });
            //modify-password-trading
          }).catch(() => {

          });
        } else if (!this.setPayPassword) {
          this.$confirm(
            this.$t('m.c2c.setTradeInfo'),
            this.$t('m.prompt'),
            {
              confirmButtonText: this.$t('m.userCenter.goSetting'),
              cancelButtonText: this.$t('m.no'),
              type: 'warning'
            }
          ).then(() => {
            this.$router.push("/usercenter/complete-userinfo")
            //modify-password-trading
          }).catch(() => {

          });

        } else {
          this.dialogVisible = true
          // 重新填入ruleForm的名称
          this.bankcardForm.name = this.realName
        }
      },
      onCopy: function (e) {
        this.$message({
          message: 'Copied',
          type: 'success'
        });
      },
      onError: function (e) {
        this.$message('Copy failed');
      },
      buyBtcxAlert() {
        this.buyBtcxDialogVisible = true
        /*let lang = this.lang;
        let text = {
          'zh-CN':'买入成功，请在30分钟内完成付款。',
          'zh-HK':'買入成功，請在30分鐘內完成付款。',
          'en-US':'The purchase was successful. Please complete the payment within 30 minutes.'
        }
        this.$alert(text[lang], this.$t('m.prompt'), {
          type             : 'success',
          confirmButtonText: this.$t('m.yes'),
          callback         : action => {
            this.buyBtcxDialogVisible = true
          }
        });*/
      },
      sellBtcxAlert() {
        this.$alert(this.$t('m.c2c.sellSuccess'), this.$t('m.prompt'), {
          type: 'success',
          confirmButtonText: this.$t('m.yes'),
          callback: action => {
//            this.$message({
//              type   : 'info',
//              message: `action: ${ action }`
//            });
            this.sellBtcxDialogVisible = false
          }
        });
      },

      withdrawAlert(desc) {

        this.$alert(desc, this.$t('m.prompt'), {
          type: 'error',
          confirmButtonText: this.$t('m.yes'),
          callback: action => {
//            this.$message({
//              type   : 'info',
//              message: `action: ${ action }`
//            });
          }
        });
      },

      submitSellBtcxForm() {
        if(this.sellBtcxForm.validateCode === '') {
          this.$message({
            message : this.$t('m.c2c.inputVCode'),
            type : 'error',
          });
          return;
        }
        if(this.sellBtcxForm.payPassword === '') {
          this.$message({
            message : this.$t('m.userCenter.plzFundPassword'),
            type : 'error',
          });
          return;
        }
        financeApi.serverCreateWithdrawals(
          this.feeRateAndBank.coinId,
          this.getWithdrawAmount,
          this.sellBtcxForm.num,
          this.sellBtcxForm.payPassword,
          this.sellBtcxForm.validateCode,
          this.token
        ).then((res) => {
          this.sellBtcxForm.num = '0';
          this.sellBtcxForm.payPassword = '';
          this.sellBtcxForm.validateCode = '';
          this.sellBtcxAlert();
          this._getUserAssets();

        }).catch((res) => {
          this.withdrawAlert(res.errmsg)
        })
      },
      formatDate(row) {
        return util.date2DateTimeString(new Date(row.createTime))
      },
      formatRealAmmout(row) {
        return row.num - row.fee
      },
      formatBankcard(row) {
        return util.bankcardFormat(row.bankCard)
      },
      formatRechargeType(row) {
        return util.formatRechargeType(row.type)
      },
      formatRechargeStatus(row) {
        return util.formatRechargeStatus(row.status)
      },
      formatRechargeStatus2(status) {
        return util.formatRechargeStatus(status)
      },
      formatWithdrawStatus(row) {
        return util.formatWithdrawStatus(row.status)
      },
//      filterHandler(value, row, column) {
//        console.log("筛选",value,column);
//        this.status = value
//        return row['status'] == value;
//      },
      handleRechargeRecord(row) {
        console.log("row", row);
        this.rechargeInfo = {
          bankCard: row.bankCard,
          bankName: row.bankName,
          status: row.status,
          name: row.name
        };
        this.rechargeRemark = row.remark
        this.rechargeRealNum = row.mum
        this.buyBtcxDialogVisible = true

      },
      onStateChange() {
        if (this.currentTab) {
          this._serverSearchWithdrawalsList()
        } else {
          this._serverSearchPaymentList()
        }
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            financeApi.serverCreateUserBank(
              (this.bankData.id) ? this.bankData.id : 0,
              this.bankcardForm.name,
              this.bankcardForm.remark,
              this.bankcardForm.bank,
              /*this.bankcardForm.bankRegion[0], this.bankcardForm.bankRegion[1], this.bankcardForm.bankDetailName,*/
              this.bankcardForm.bankcard,
              this.bankcardForm.payPassword,
              this.token).then(res => {

              this.dialogVisible = false
              this._serverUserBankList();
              // 清除表单数据
              this.resetForm("bankcardForm")
//                this.bankcardForm.bankRegion = []

              this.$message({
                message: "添加成功",
                type: "success"
              });
            }).catch(res => {
              this.$message({
                message: res.errmsg,
                type: "error"
              });
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },

      _bankcardFormat(bankcard) {
        return util.bankcardFormat(bankcard)
      },
      handlePageChange(page) {
        this.page = page
        if (this.currentTab == 0) {
          this._serverSearchPaymentList()
        } else {
          this._serverSearchWithdrawalsList()
        }
      },
      _serverSearchPaymentList() {
        financeApi.serverSearchPaymentList(this.rows, this.page, this.statusValue, this.token).then(res => {
          console.log("充值记录", res.data);
          this.cnyRechargeRecord = res.data.records
          this.totalRecords = res.data.total
        }).catch(() => {
        })
      },
      _serverSearchWithdrawalsList() {
        financeApi.serverSearchWithdrawalsList(this.rows, this.page, this.statusValue, this.token).then(res => {
          console.log("提现记录", res.data);
          this.cnyWithdrawRecord = res.data.records
          this.totalRecords = res.data.total
        }).catch(() => {
        })
      },

      _serverUserBankList() {
        let lang = this.lang;
        financeApi.serverUserBank(this.token).then(res => {
          console.log("银行卡信息", res)
          if (!res.data) {
            this.addOrModifyBankcard = ''
          } else {
            this.bankData = res.data
            this.addOrModifyBankcard = res.data.bank;
          }
        })
      },

      handleClose() {
        done();
      },
      handleTabClick(tab, event) {
        this.statusValue = 4;
        this.statusOptions = this.statusI18nMap[this.activeName][this.lang];
        if (tab.index == 1) {
          this.currentTab = 1
          this._serverSearchWithdrawalsList()
        } else {
          this.currentTab = 0
          this._serverSearchPaymentList()
        }
      }
    },
    components: {
      NavHeader,
      mFooter,
      financeHeader,
      validateCode
    },
  }
</script>
<style lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/usercenter"

  .ctoc {
    .exchange .el-form-item__label {
      display block
      line-height 20px
    }
    .el-tabs__nav-wrap::after {
      background-color #fff
    }
    .el-dialog__body {
      padding: 0
    }
  }
  .el-select-dropdown{
    z-index 2111 !important
  }
  .el-form-item__label {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
</style>
<style scoped lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/variable"
  .ucenter-wrap {
    .ucenter-main {
      .wallet {
        background #fff
        box-shadow 0 0 10px rgba(0, 0, 0, .2)
        height 120px
        padding 0 20px
        border-radius 4px
        display flex
        align-items center
        .wallet-image {
          padding-right 20px
        }
        .wallet-balance {
          line-height 21px
          span {
            font-weight bold
          }
          p {
            font-size 16px
            color #454545
          }
        }
        .wallet-card-info {
          line-height 20px
          p {
            font-size 14px
            color #454545
          }
        }
        .bankcard {
          display flex
          align-items center
          padding-right 20px
        }
        .add-bankcard {
          border-left 1px solid rgba(170, 171, 177, .5)
          height 60px
          display: flex;
          align-items center
          padding-left 20px
          a {
            color #7392FF
          }
        }
      }

      .exchange {
        margin-top 20px
      }

      .label-row {
        display flex
        justify-content space-between
      }
      .price {
        margin-right 10px
        font-weight bold
        &.green {
          color #0BB474
        }
        &.red {
          color #F61F33
        }
      }

      .sub-title {
        color $color-global-text-light
        font-size 14px
      }

      .btn-btcx-sell {
        width 100%
        background-color #F61F33
        border-color #F61F33
      }
      .btn-btcx-sell,
      .btn-btcx-sell:hover {
        color #fff
      }

      .notice-panel {
        margin-top 30px
        padding 25px 30px
        background #fff
        box-shadow 0 0 10px rgba(0, 0, 0, .2)
        color $color-global-text-light
        font-size 16px
        border-radius 4px
        margin-bottom 20px
        .notice-title {
          margin-bottom 15px
        }
        ul {
          margin-left 20px
          li {
            list-style disc
            line-height 1.77
            &.red {
              color #F61F33
            }
          }
        }
      }

      .table-wrap {
        position relative
        margin-top 30px
        .select-container {
          position absolute
          right 0
          top 5px
          z-index 9
        }
      }
    }

    .ucenter-dialog {
      .dialog-title {
        font-size 18px
        margin-bottom 15px
      }
      .dialog-form {
        margin-top 15px
        z-index 9999999

        .submit-withdraw-container {
          text-align right
        }
      }
    }

    .sell-input-group{
      margin-bottom 20px
      font-size 14px
      label{
        margin-bottom 10px
        display block
      }
    }
    .sell-input-group.first{
      margin-top 30px
    }

    .remark-red {
      color #f00
    }
  }
</style>
