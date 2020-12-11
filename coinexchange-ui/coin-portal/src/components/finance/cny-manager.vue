<template>
  <div class="cny-manager-main">
    <NavHeader activeTab="3" ref="myHeader"></NavHeader>
    <div class="main" ref="myHeader">
      <div class="content">
        <financeHeader first="资产管理" second="CNY管理" :noborder="true" jumpto="/finance"></financeHeader>
        <div class="bankcard-container">
          <div class="bankcard-container-left">
            <img src="/static/img/finance/bankcard.png" alt="">
            <div class="rmb-amount">
              <p>CNY可用额度 <span>{{balanceAmount}} CNY</span></p>
              <p>CNY冻结额度 <span>{{freezeAmount}} CNY</span></p>
            </div>
          </div>
          <div class="bankcard-container-divider">

          </div>
          <div class="bankcard-container-right">
            <div class="bankcard-item" v-for="(bankItem,index) in bankList" v-show="index<2">

              <p class="bank-title">
                <!--<img class="bank-icon" src="/static/img/finance/gongshang-bank.png"
                                         alt="">-->
                {{bankItem.bank}}
                <!--<img src="/static/img/finance/trash.png" class="bank-trash">-->
              </p>
              <div class="account-container">
                <p class="bankcard-username">{{bankItem.trueName}}</p>
                <p class="bankcard-account">{{_bankcardFormat(bankItem.bankCard)}}</p>
                <p>充值币种：{{bankItem.coinName.toUpperCase()}}</p>
              </div>
            </div>
            <!--<div class="bankcard-item" v-show="false">-->

            <!--</div>-->

            <div class="bankcard-item-add" @click="handleAddBankCard">
              <img class="add-bankcard" src="/static/img/finance/add-bankcard.png"/>
              <p>添加您的银行卡</p>
            </div>
          </div>
        </div>
        <!--充值 提现-->
        <div class="recharge-withdraw-container">
          <div class="recharge-withdraw-content">
            <el-tabs v-model="activeName" @tab-click="handleTabClick">
              <el-tab-pane label="充值" name="first">
                <div class="recharge-container">
                  <el-form :model="rechargeForm" :rules="rechargeRules" ref="rechargeForm" :inline="true"
                           class="recharge-label">
                    <el-form-item label="充值金额" prop="rechargeNum">
                      <el-input v-model="rechargeForm.rechargeNum" type="number" class="recharge-input"
                                placeholder="请输入充值金额" :min="1"></el-input>
                    </el-form-item>
                    <el-button @click="submitRecharge('rechargeForm')" class="recharge-btn" type="primary">充值
                    </el-button>

                  </el-form>
                  <div class="recharge-container-content">
                    <div class="remark-container">
                      <p class="pay-title">充值订单</p>
                      <div class="remark-content">
                        <div class="remark-content-item">
                          <p class="remark-real">充值金额　　 {{rechargeRealNum}}</p>
                          <p class="alipay-username">充值序列号　<el-tag type="danger" v-show="rechargeRemark">{{rechargeRemark}}</el-tag>
                          <el-button
                            v-clipboard:copy="rechargeRemark"
                            v-clipboard:success="onCopy"
                            v-clipboard:error="onError"
                            :plain="true"
                            type="primary" size="mini" v-show="rechargeRemark">复制</el-button>

                          </p>
                          <p class="recharge-remark-tips">注意：汇款时请一定要填写备注信息</p>

                        </div>


                      </div>
                    </div>
                    <!--<div class="alipay-container">-->
                      <!--<p class="pay-title">支付宝充值</p>-->
                      <!--<div class="alipay-content">-->
                        <!--<div class="alipay-content-left">-->
                          <!--<p>账号，请放心付款</p>-->
                        <!--</div>-->
                        <!--<div class="alipay-content-right">-->
                          <!--<img src="/static/img/finance/company-alipay.png">-->
                        <!--</div>-->
                      <!--</div>-->

                    <!--</div>-->
                    <div class="bankcard-pay-container">
                      <p class="pay-title">网银极速付款</p>
                      <div class="bankcard-content">
                        <!--<div class="bankcard-content-left">-->
                        <div class="bankcard-content-item">
                          <p>银行账号</p>
                          <p>开户名</p>
                          <p>开户行</p>
                        </div>
                        <div class="bankcard-content-item">
                        <!--<p>6230 5201 2002 5510 177</p>-->
                        <!--<p>杨梅</p>-->
                        <!--<p>中国农业银行股份有限公司深圳环新支行</p>-->
                        <p>6230 5201 2002 5510 177</p>
                        <p>杨梅</p>
                        <p>中国农业银行深圳环新支行</p>
                      </div>
                        <!--</div>-->
                      </div>


                    </div>
                  </div>
                </div>

                <div class="horizontal-divider"></div>
                <div class="recharge-withdraw-tips">
                  <p class="recharge-withdraw-tips-title">温馨提示</p>
                  <p class="recharge-withdraw-tips-subtitle">尊敬的hht.one用户：为了您的购买可以快速到账，请在交易前仔细阅读以下注意事项：</p>

                  <div class="ul-container">
                    <ul>
                      <li>在平台发起充值申请后，请使用平台公布的汇款方式进行汇款</li>
                      <li>商家处理时间9:00 - 21:00非处理时间的订单会在第二天9:00开始处理，接单后2小时内会完成交易，时间不会超过24小时。请在下单后30分钟内完成付款，否则会延时到账</li>
                      <!--<li>禁止使用支付宝、他人代付、ATM及柜台转账等第三方转账</li>-->
                      <li>请使用本人绑定的银行卡通过手机银行或网银进行汇款，其他任何方式汇款都会造成延时处理</li>
                      <li>如果你的交易24小时内没有处理，请联系在线客服登记。会有专人24小时内与您联系，请保持手机畅通</li>
                      <li>
                        由于咨询量过大，24小时内未处理的交易订单咨询，在线客服无法提供帮助。敬请谅解
                      </li>
                    </ul>
                  </div>


                </div>
                <div class="horoizontal-divider"></div>

                <!--CNY充值记录-->
                <div class="cny-recharge-withdraw-record">
                  <p class="cny-recharge-title">CNY充值记录</p>
                  <div class="select-container">
                    <el-select v-model="rechargeStatusValue" placeholder="全部状态" @change="onStateChange">
                      <el-option
                        v-for="item in rechargeStatusOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                  </div>

                  <el-table
                    :data="cnyRechargeRecord"
                    header-cell-class-name="tabheader"
                    class="cny-table"
                  >
                    <el-table-column
                      prop="tradeNo"
                      label="充值订单"
                      align="center"
                      width="220"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="createTime"
                      label="充值时间"
                      align="center"
                      :formatter="formatDate"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="type"
                      label="充值方式"
                      align="center"
                      :formatter="formatRechargeType"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="num"
                      label="充值金额"
                      align="center"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="num"
                      label="实际到账"
                      align="center"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="status"
                      label="状态"
                      align="center"
                      :formatter="formatRechargeStatus"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="status"
                      label="操作"
                      align="center"

                    >
                      <template slot-scope="scope">
                        <el-button @click="handleRechargeRecord(scope.row)" :disabled="scope.row.status!=0">线下付款
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
              <el-tab-pane label="提现" name="second">
                <div class="withdraw-container">
                  <div class="withdraw-content">
                    <div class="withdraw-input-container">
                      <el-form :model="withdrawForm" :rules="withdrawRules" ref="withdrawForm" label-width="130px"
                               :inline="true">
                        <!--<div class="withdraw-content-item">-->
                        <el-form-item label="选择银行" prop="bankId">
                          <el-select v-model="withdrawForm.bankId" placeholder="请选择银行" class="withdraw-input-area">
                            <el-option
                              v-for="item in bankListOptionWithdraw"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value">
                            </el-option>
                          </el-select>
                        </el-form-item>
                        <!--</div>-->

                        <!--<div class="withdraw-content-item">-->
                        <el-form-item label="提现金额" prop="num">
                          <el-input placeholder="请输入提现金额" v-model="withdrawForm.num"
                                    class="withdraw-input-area"
                                    type="number" :min="1" auto-complete="off"/>
                        </el-form-item>
                        <!--</div>-->

                        <!--<div class="withdraw-content-item">-->
                        <el-form-item label="验证码" prop="validateCode">
                          <el-input placeholder="请输入验证码" v-model="withdrawForm.validateCode" class="withdraw-input-area"
                                    :maxlength="6" auto-complete="off">
                            <template slot="append">
                              <validateCode :mobile="mobile"></validateCode>
                            </template>
                          </el-input>
                        </el-form-item>

                        <!--</div>-->
                        <!--<div class="withdraw-content-item">-->
                        <el-form-item label="交易密码" prop="payPassword">
                          <el-input placeholder="请输入交易密码" v-model="withdrawForm.payPassword" class="withdraw-input-area"
                                    type="password" auto-complete="off" />
                        </el-form-item>

                        <el-form-item label="手续费率">
                          <el-tag type="danger">1%</el-tag>
                        </el-form-item>
                        <!--</div>-->

                      </el-form>
                    </div>
                    <el-button class="withdraw-confirm" type="primary" @click="submitWithdrawForm('withdrawForm')">
                      确认提现
                    </el-button>


                  </div>

                </div>
                <div class="horizontal-divider"></div>
                <div class="recharge-withdraw-tips">
                  <p class="recharge-withdraw-tips-title">温馨提示</p>
                  <p class="recharge-withdraw-tips-subtitle">尊敬的hht.one用户：为了您的购买可以快速到账，请在交易前仔细阅读以下注意事项：</p>

                  <div class="ul-container">
                    <ul>
                      <li>在平台发起充值申请后，请使用平台公布的汇款方式进行汇款</li>
                      <li>商家处理时间9:00 - 21:00非处理时间的订单会在第二天9:00开始处理，接单后2小时内会完成交易，时间不会超过24小时。请在下单后30分钟内完成付款，否则会延时到账</li>
                      <!--<li>禁止使用支付宝、他人代付、ATM及柜台转账等第三方转账</li>-->
                      <li>请使用本人绑定的银行卡通过手机银行或网银进行汇款，其他任何方式汇款都会造成延时处理</li>
                      <li>如果你的交易24小时内没有处理，请联系在线客服登记。会有专人24小时内与您联系，请保持手机畅通</li>
                      <li>
                        由于咨询量过大，24小时内未处理的交易订单咨询，在线客服无法提供帮助。敬请谅解
                      </li>
                    </ul>
                  </div>


                </div>
                <div class="horizontal-divider"></div>
                <!--CNY提现记录-->
                <div class="cny-recharge-withdraw-record">
                  <p class="cny-recharge-title">CNY提现记录</p>
                  <el-table
                    :data="cnyWithdrawRecord"
                    header-cell-class-name="tabheader"
                    class="cny-table"
                  >
                    <el-table-column
                      prop="createTime"
                      label="充值时间"
                      align="center"
                      :formatter="formatDate"
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
                      label="提现金额"
                      align="center"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="fee"
                      label="手续费"
                      align="center"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="diff"
                      label="实际到账"
                      align="center"
                      :formatter="formatRealAmmout"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="bankCard"
                      label="银行卡号"
                      align="center"
                      :formatter="formatBankcard"
                    >
                    </el-table-column>
                    <el-table-column
                      prop="status"
                      label="状态"
                      align="center"
                      :formatter="formatWithdrawStatus"
                    >
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
      <!--添加银行卡弹窗-->
      <vodal :show="dialogVisible" animation="fade" @hide="dialogVisible = false" class="addbankcard-dialog"
             :width="530" :height="600" :closeOnEsc="true">
        <p class="dialog-title">添加银行卡</p>

        <el-form :model="bankcardForm" :rules="bankcardRules" ref="bankcardForm" label-width="130px">
          <p class="dialog-tips">只可以添加本人的银行卡</p>
          <el-form-item label="开户名" prop="name">
            <el-input v-model="bankcardForm.name" placeholder="请输入开户名" class="dialog-input"
                      auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="备注名" prop="remark">
            <el-input v-model="bankcardForm.remark" placeholder="请输入备注名" class="dialog-input"
                      auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="开户银行" prop="bank">
            <el-select v-model="bankcardForm.bank" placeholder="请选择银行" class="dialog-input" auto-complete="off">
              <el-option
                v-for="item in bankListOptionFixed"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>

          <!--<el-form-item label="开户银行所在地" prop="bankRegion">-->
            <!--<area-select v-model="bankcardForm.bankRegion" class="city-select" auto-complete="off"></area-select>-->
          <!--</el-form-item>-->

          <!--<el-form-item label="开户支行名称" prop="bankDetailName">-->
            <!--<el-input v-model="bankcardForm.bankDetailName" placeholder="请输入开户支行名称" class="dialog-input"-->
                      <!--auto-complete="off"></el-input>-->
          <!--</el-form-item>-->
          <el-form-item label="银行卡号" prop="bankcard">
            <el-input v-model="bankcardForm.bankcard" placeholder="填写正确的银行卡号，不必用空格隔开！" class="dialog-input"
                      auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="重复卡号" prop="bankcardConfirm">
            <el-input v-model="bankcardForm.bankcardConfirm" placeholder="请确认银行卡号" class="dialog-input"
                      auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="交易密码" prop="payPassword">
            <el-input v-model="bankcardForm.payPassword" placeholder="请输入交易密码" class="dialog-input" type="password"
                       auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('bankcardForm')" class="addbankcard-btn">添加</el-button>
          </el-form-item>
        </el-form>
      </vodal>

      <!--支付弹窗-->
      <vodal :show="rechargeDialogVisible" animation="fade" @hide="rechargeDialogVisible = false"
             class="recharge-dialog"
             :width="530" :height="450" :closeOnEsc="true">
        <p class="dialog-title">账户充值</p>
        <div class="recharge-container-content">
          <div class="remark-container">
            <p class="pay-title">充值订单</p>
            <div class="remark-content">
              <div class="remark-content-item">
                <p class="remark-real">充值金额　　 {{rechargeDialog.rechargeRemark}}</p>
                <p class="alipay-username">充值序列号　 {{rechargeDialog.rechargeRealNum}}</p>
                <p class="recharge-remark-tips">注意：汇款时请一定要填写备注信息</p>
              </div>
            </div>
          </div>
          <div class="alipay-container">
            <!--<p class="pay-title">支付宝充值</p>-->
            <!--<div class="alipay-content">-->
              <!--<div class="alipay-content-left">-->
                <!--<p class="alipay-account">该二维码为平台支付宝</p>-->
                <!--<p>账号，请放心付款</p>-->
              <!--</div>-->
              <!--<div class="alipay-content-right">-->
                <!--<img src="/static/img/finance/company-alipay.png">-->
              <!--</div>-->
            <!--</div>-->

          </div>
          <div class="bankcard-pay-container">
            <p class="pay-title">网银极速付款</p>
            <div class="bankcard-content">
              <!--<div class="bankcard-content-left">-->
              <div class="bankcard-content-item">
                <p>银行账号</p>
                <p>开户名</p>
                <p>开户行</p>
              </div>
              <div class="bankcard-content-item">

                <p>6230 5201 2002 5510 177</p>
                <p>杨梅</p>
                <p>中国农业银行 深圳环新支行</p>
              </div>
              <!--</div>-->
            </div>


          </div>
        </div>
      </vodal>
    </div>
    <mFooter></mFooter>
  </div>
</template>
<script>
  import ToggleButton from 'vue-js-toggle-button'
  import Vue from 'vue'
  import NavHeader from 'components/nav-header/nav-header'
  import mFooter from 'components/m-footer/m-footer'
  import VueClipboard from 'vue-clipboard2'
  import dom from 'common/js/dom'
  import { financeApi } from 'api/finance'
  import { util } from 'common/js/util'
  import  validateCode  from 'base/validate-code/validate-code'

  import { OK } from 'api/config'
  import VueAreaLinkage from 'vue-area-linkage';
  import financeHeader from './finance-header'
  import Vodal from 'vodal';

  Vue.component(Vodal.name, Vodal);

  Vue.use(VueAreaLinkage)
  Vue.use(VueClipboard)
  Vue.use(ToggleButton)
  export default {
    data(){

      let validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次银行卡号'));
        } else if (value !== this.bankcardForm.bankcard) {
          callback(new Error('两次输入银行卡号不一致!'));
        } else {
          callback();
        }
      };

      let validateRechargeNum = (rule, value, callback) => {
        if (value < 100) {
          callback(new Error('充值金额最小100元'));
        } else {
          callback();
        }
      };
      let validateWithdrawNum = (rule, value, callback) => {
        if (value < 100) {
          callback(new Error('提现金额必须大于100'));
        } else {
          callback();
        }
      };
      return {
        // 添加银行卡表单规则
        bankcardForm          : {
          remark         : '',
          name           : '',
          bank           : '',
          bankRegion     : [],
          bankDetailName : '',
          bankcard       : '',
          bankcardConfirm: '',
          payPassword    : ''
        },
        bankcardRules         : {
          remark         : [
            {required: true, message: '请输入备注名', trigger: 'blur'},
          ],
          name           : [
            {required: true, message: '请输入开户名', trigger: 'blur'},
          ],
          bank           : [
            {required: true, message: '请选择银行', trigger: 'change'}
          ],
          bankRegion     : [
            {required: true, message: '请选择开户银行所在地', trigger: 'change'}
          ],
          bankDetailName : [
            {required: true, message: '请输入开户支行名称', trigger: 'blur'}
          ],
          bankcard       : [
            {required: true, message: '请输入银行卡号', trigger: 'blur'}
          ],
          bankcardConfirm: [
            {required: true, message: '请再次输入银行卡号', trigger: 'blur'},
            {validator: validatePass, trigger: 'blur'}
          ],
          payPassword    : [
            {required: true, message: '请输入交易密码', trigger: 'blur'}
          ]
        },
        // 提现规则
        withdrawForm          : {
          bankId      : '',
          num         : '',
          // 验证码
          validateCode: '',
          payPassword : ''
        },
        withdrawRules         : {
          bankId      : [{required: true, message: '请选择银行卡', trigger: 'blur'},
          ],
          num         : [{required: true, message: '请输入提现金额', trigger: 'blur'},
            {validator: validateWithdrawNum, trigger: 'blur'}
          ],
          validateCode: [{required: true, message: '请输入验证码', trigger: 'blur'},
          ],
          payPassword : [{required: true, message: '请输入交易密码', trigger: 'blur'},
          ]
        },
        rechargeForm          : {
          rechargeNum: ''
        },
        rechargeRules      : {
          rechargeNum: [{required: true, message: '请输入充值金额', trigger: 'blur'},
            {validator: validateRechargeNum, message: '充值金额最小100元', trigger: 'blur'}
          ],
        },
        // 用户手机号
        mobile             : '',
        dialogVisible      : false,
        rows               : 5,
        page               : 1,
        totalRecords       : 0,
        currentTab         : 0,
        cnyRechargeRecord  : [],
        cnyWithdrawRecord  : [],
        activeName         : 'first',
        balanceAmount      : 0,
        freezeAmount       : 0,
        bankData           : [],
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
        }],
//        提现银行列表
        bankListOptionWithdraw: [],
        // 人民币充值
        rechargeNum           : '',
        rechargeRemark        : '',
        rechargeRealNum       : '',
        rechargeStatus        : -1,
        rechargeStatusValue   : '',
        // 充值记录status -1

        rechargeStatusOptions: [{
          label: "未付款",
          value: 0
        }, {
          label: "充值成功",
          value: 1
        }, {
          label: "人工到账",
          value: 2
        }, {
          label: "处理中",
          value: 3
        }],
//        支付弹窗
        rechargeDialogVisible: false,
        rechargeDialog       : {
          rechargeRemark : '',
          rechargeRealNum: ''
        }

      }
    },
    created(){

    },
    mounted(){
//      this.$refs.dialog.open(function () {
//        console.log("open");
//      });
//      setTimeout(() => {
//        this.$refs.dialog.close(function () {
//          console.log("close");
//
//        });
//
//      }, 2000)

      this._serverUserBankList()
      this._serverGetUserAccountByCoinId()
      this._serverSearchPaymentList()

    },
    computed  : {
      getMobible() {
        this.mobile = this.$store.state.mobile
        return this.$store.state.mobile;
      },
      getTrueName(){
        this.bankcardForm.name = this.$store.state.trueName
        return this.$store.state.trueName;
      }
    },
    watch     : {
      getMobible(val) {
        console.log("监听手机号", val);
        this.mobile = val;
      },
      getTrueName(val){
        this.bankcardForm.name = val
      }
    },
    methods   : {
      handleAddBankCard(){
        if(this.$store.state.isAuth == 0){
          this.$confirm('您尚未进行实名认证, 是否前去设置?', '提示', {
            confirmButtonText: '去设置',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$router.push("/usercenter/modify-id")
            //modify-password-trading
          }).catch(() => {

          });
        }else if(!this.$store.state.setPayPassword){
          this.$confirm('您还有交易信息待完善, 是否前去设置?', '提示', {
            confirmButtonText: '去设置',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$router.push("/usercenter/complete-userinfo")
            //modify-password-trading
          }).catch(() => {

          });

        }else{
          this.dialogVisible=true
        }
      },
      onCopy : function (e) {
        this.$message({
          message: '复制成功',
          type   : 'success'
        });
      },
      onError: function (e) {
        this.$message('复制失败');
      },
      rechargeAlert() {
        this.$alert('充值申请成功，请按照下方订单信息进行充值。', '提示', {
          type:'success',
          confirmButtonText: '确定',
          callback         : action => {
//            this.$message({
//              type   : 'info',
//              message: `action: ${ action }`
//            });
          }
        });
      },
      withdrawAlert(desc){
        this.$alert(desc, '提示', {
          type:'error',
          confirmButtonText: '确定',
          callback         : action => {
//            this.$message({
//              type   : 'info',
//              message: `action: ${ action }`
//            });
          }
        });
      },

//    获得充值备注
      submitRecharge(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let rechargeAmount = Number(this.rechargeForm.rechargeNum) + util.rechargeFormat();
            console.log(this.rechargeForm.rechargeNum, rechargeAmount);

            financeApi.serverRecharge(rechargeAmount,
              "linepay").then(res => {
              if (res.statusCode === OK) {
                console.log("充值成功：");
                this.rechargeAlert()
                this.rechargeRemark  = res.result.remark
                this.rechargeRealNum = res.result.num
                //刷新充值记录
                this._serverSearchPaymentList()

              } else {

              }
            })
          }
        })
      },
      submitWithdrawForm(formName) {

        this.$refs[formName].validate((valid) => {
          if (valid) {
            console.log(this.withdrawForm.bankId, this.withdrawForm.num,
              this.withdrawForm.validateCode, this.withdrawForm.payPassword);

            financeApi.serverCreateWithdrawals(this.withdrawForm.bankId,
              this.withdrawForm.num, this.withdrawForm.validateCode, this.withdrawForm.payPassword).then(res => {
              if (res.statusCode === OK) {
                console.log("提现申请成功：");
                this.$message({
                  message: "提现申请成功",
                  type   : "success"
                })
              } else {
                this.withdrawAlert(res.errmsg)
              }

            })
          }
        })
      },
      formatDate(row){
        return util.date2DateTimeString(new Date(row.createTime))
      },
      formatRealAmmout(row){
        return row.num - row.fee
      },
      formatBankcard(row){
        return util.bankcardFormat(row.bankCard)
      },
      formatRechargeType(row){
        return util.formatRechargeType(row.type)
      },
      formatRechargeStatus(row){
       return util.formatRechargeStatus(row.status)
      },
      formatWithdrawStatus(row){
       return util.formatWithdrawStatus(row.status)
      },
//      filterHandler(value, row, column) {
//        console.log("筛选",value,column);
//        this.status = value
//        return row['status'] == value;
//      },
      handleRechargeRecord(row){
        this.rechargeDialog.rechargeRemark  = row.remark
        this.rechargeDialog.rechargeRealNum = row.num
        this.rechargeDialogVisible          = true

      },
      onStateChange(value){
        console.log("状态变化：", value);
        this.rechargeStatus = value
        this._serverSearchPaymentList()
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            financeApi.serverCreateUserBank(this.bankcardForm.name, this.bankcardForm.remark, this.bankcardForm.bank, /*this.bankcardForm.bankRegion[0], this.bankcardForm.bankRegion[1], this.bankcardForm.bankDetailName,*/ this.bankcardForm.bankcard, this.bankcardForm.payPassword).then(res => {
              if (res.statusCode === OK) {
                this.dialogVisible = false
                this._serverUserBankList();
                // 清除表单数据
                this.resetForm("bankcardForm")
//                this.bankcardForm.clear()
//                this.bankcardForm.bankRegion = []
                this.$message({
                  message: "添加成功",
                  type   : "success"
                })
              } else {
                this.$message({
                  message: res.errmsg,
                  type   : "error"
                })
              }
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

      _bankcardFormat(bankcard){
        return util.bankcardFormat(bankcard)
      },
      handlePageChange(page){
        this.page = page
        if (this.currentTab == 0) {
          this._serverSearchPaymentList()
        } else {
          this._serverSearchWithdrawalsList()
        }
      },
      _serverSearchPaymentList(){
        financeApi.serverSearchPaymentList(this.rows, this.page, this.rechargeStatus).then(res => {
          if (res.statusCode === OK) {
            console.log("充值记录", res.result);
            this.cnyRechargeRecord = res.result.result
            this.totalRecords      = res.result.totalRecords
          }
        })
      },
      _serverSearchWithdrawalsList(){
        financeApi.serverSearchWithdrawalsList(this.rows, this.page).then(res => {
          if (res.statusCode === OK) {
            console.log("提现记录", res.result);
            this.cnyWithdrawRecord = res.result.result
            this.totalRecords      = res.result.totalRecords
          }
        })
      },
      _serverGetUserAccountByCoinId(){
        financeApi.serverGetUserAccountByCoinId().then(res => {
          if (res.statusCode === OK) {
            console.log("cny账户：", res.result);
            this.balanceAmount = res.result.balanceAmount
            this.freezeAmount  = res.result.freezeAmount
          } else {

          }
        })
      },
      _serverUserBankList(){
        financeApi.serverUserBank().then(res => {
          if (res.statusCode === OK) {
            this.bankList = res.result
            console.log("银行列表：", this.bankData);
            for (let i in res.result) {
              let item      = res.result[i]
              let newItem   = {}
              newItem.value = item.id
              newItem.label = item.remark
              this.bankListOptionWithdraw.push(newItem)
            }
          } else {

          }
        })
      },

      handleClose(){
        done();
      },
      handleTabClick(tab, event) {
        console.log(tab);
        if (tab.index == 1) {
          this.currentTab = 1
          console.log("if");
          this._serverSearchWithdrawalsList()
        } else {
          this.currentTab = 0
          console.log("else");
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
<style scope lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/variable"
  .cny-manager-main {
    background #fff
    .addbankcard-dialog {

    //        加了scoped 都改不了
      .dialog-title {
        /*background $color-global-tb-header-bg-light
        border-bottom 1px solid $color-global-border
        height 50px
        line-height 50px*/
      }
      .dialog-tips {
        margin-left 130px
        margin-top 20px
        margin-bottom 5px
        font-size $font-size-small
        color #f56c6c
      }
      .area-select .el-select {
        margin-left 0
        color #f00
      }
      .addbankcard-btn {
        margin 0 auto
        width: 324px;
      }
      .dialog-input {
        width: 324px;
      }

    }

    .recharge-dialog {
      .recharge-container-content {
        display flex
        justify-content space-between
        flex-direction column
        align-items center
        .pay-title {
          color $color-global-text-dark
          font-size $font-size-medium-x
          height 40px
          line-height: 40px
        }
        font-size $font-size-medium
        color $color-global-text-dark
      // 上中下
        .remark-container {
          width 460px
          height 210px
          .remark-content {
            height 150px
            background $color-global-tb-header-bg-light
            padding 0 20px
            display flex
            align-items center
            .remark-content-item {
              height 110px
              display flex
              flex-direction column
              justify-content space-around
            }
            .recharge-remark-tips {
              color $color-global-red
            }
          }
        }
      // 中
      /*  .alipay-container {
          width 460px
          height 210px

          .alipay-content {
            height 150px
            padding 0 20px
            background $color-global-tb-header-bg-light
            display flex
            justify-content space-around
            align-items center

            .alipay-content-left {
              display flex
              align-items flex-start
              flex-direction column
              justify-content flex-start
              height 110px
              flex 6
              .alipay-account {
                margin 10px 0
              }
            }
            .alipay-content-right {
              flex 4
              vertical-align middle
              text-align center
              img {
                width 110px
                height 110px
              }
            }
          }
        }*/
      // 下
        .bankcard-pay-container {
          width 460px
          height 210px
          .bankcard-content {
            background $color-global-tb-header-bg-light

            height 150px
            padding 0 20px
            display flex
            justify-content space-around
            align-items center
            padding-right: 100px;

            .bankcard-content-item {
              display flex
              justify-content space-around
              flex-direction column
              height 110px
            }
          }
        }
      }
    }
    .content {
      width 1200px
      margin 0 auto
      /*选项卡重写*/
      .el-tabs .el-tabs__header .el-tabs__nav-wrap::after {
        background-color: #DDDDDD
        height 1px
      }
      .el-tabs .el-tabs__header .el-tabs__nav-wrap .el-tabs__nav-scroll .el-tabs__nav .el-tabs__item {
        color: #333333
      }
      .el-tabs .el-tabs__header .el-tabs__nav-wrap .el-tabs__nav-scroll .el-tabs__nav .el-tabs__item.is-active {
        color: #00A9FE
        height 1px
      }

      .title {
        margin-top 20px
        height 50px
        line-height 50px
        padding 0 20px
        font-size $font-size-medium

        color $color-global-text-dark
        border-right 1px solid $color-global-border
        border-left 1px solid $color-global-border
        border-top 1px solid $color-global-border
        span {
          font-size $font-size-medium
        }
      }
      .bankcard-container {
        margin-top 16px

        display: flex
        justify-content space-between
        align-items center
        border: 1px solid $color-global-border

        .bankcard-container-left {
          flex 4

          display flex
          justify-content flex-start
          align-items center
          padding-left 23px
          img {
            width 90px
            height 90px
          }
          .rmb-amount {
            font-size $font-size-medium
            color $color-global-text-light
            margin-left 20px
            display flex
            height 50px
            flex-direction column
            justify-content space-between
          }
        }
        .bankcard-container-divider {
          width 1px
          height 124px
          background $color-global-border
        }
        .bankcard-container-right {
          flex 6

          display flex
          justify-content space-around
          align-items center
          padding-left 40px
          flex-wrap: wrap
          padding-bottom 10px
          .bankcard-item-add {
            width 200px
            height 130px
            border 1px solid $color-global-border
            background #fff
            display flex
            flex-direction column
            justify-content center
            align-items center
            color $color-global-text-light
            font-size $font-size-medium
            cursor pointer
            margin-top 10px

            p {
              margin-top 14px
            }
          }
          .bankcard-item {
            width 200px
            height 130px
            border 1px solid $color-global-border
            background $color-global-tb-header-bg-light
            display flex
            flex-direction column
            margin-top 10px

            .add-bankcard {
              width 100px
              height 50px
            }
            .bank-title {
              padding-left 12px
              height 30px
              line-height 30px
              border-bottom 1px solid $color-global-border
              display flex
              align-items center
              font-size $font-size-medium
              color $color-global-text-dark
              .bank-icon {
                margin-right 5px
              }
              .bank-trash {
                margin-left 30px
                cursor: pointer

              }

            }
            .account-container {
              font-size $font-size-medium
              color $color-global-text-light
              flex 1
              display flex
              flex-direction column
              justify-content space-around
              padding-left 12px
            }
          }
        }
      }
      .recharge-withdraw-container {
        margin-bottom: 81px;
        border: 1px solid $color-global-border
        border-top none
        .recharge-withdraw-content {
          margin 0 20px
          margin-bottom 81px
          .el-form-item__label {
            font-size $font-size-medium
            color $color-global-text-dark
          }
          .recharge-container-content {
            display flex
            justify-content flex-start
            .pay-title {
              color $color-global-text-dark
              font-size $font-size-medium-x
              height 40px
              line-height: 40px
            }
            font-size $font-size-medium
            color $color-global-text-dark
          // 左
            .remark-container {
              width 350px
              height 210px
              .remark-content {
                height 150px
                background $color-finance-pay-bg
                padding 0 20px
                display flex
                align-items center
                .remark-content-item {
                  height 110px
                  display flex
                  flex-direction column
                  justify-content space-around
                  .alipay-username{

                  }
                }
                .recharge-remark-tips {
                  color $color-global-red
                }
              }
            }
          // 中
           /* .alipay-container {
              width 310px
              height 210px

              .alipay-content {
                height 150px
                padding 0 20px
                background $color-finance-pay-bg
                display flex
                justify-content space-around
                align-items center

                .alipay-content-left {
                  display flex
                  align-items flex-start
                  flex-direction column
                  justify-content flex-start
                  height 110px
                  flex 6
                  .alipay-account {
                    margin 10px 0
                  }
                }
                .alipay-content-right {
                  flex 4
                  vertical-align middle
                  text-align center
                  img {
                    width 110px
                    height 110px
                  }
                }
              }
            }*/
          // 右
            .bankcard-pay-container {
              margin-left 20px
              width 460px
              height 210px
              .bankcard-content {
                background $color-finance-pay-bg
                height 150px
                padding 0 20px
                display flex
                justify-content space-around
                align-items center
                padding-right: 100px;

                .bankcard-content-item {
                  display flex
                  justify-content space-around
                  flex-direction column
                  height 110px
                }
              }
            }
          }
          .horizontal-divider {
            height 1px
            margin-top 20px
            background $color-global-border
          }
        // 温馨提示
          .recharge-withdraw-tips {
            .recharge-withdraw-tips-title {
              font-size $font-size-medium-x
              color $color-global-text-dark
              height 40px
              line-height: 40px
            }
            .recharge-withdraw-tips-subtitle {
              font-size $font-size-medium
              color $color-global-text-dark
              height 40px
              line-height: 40px
            }

            .ul-container {
              font-size $font-size-medium
              color $color-global-text-dark
              background $color-global-tb-header-bg-light
              border: 1px solid $color-global-border
              list-style: inside;
              margin-bottom 20px
              padding-bottom 20px
              li {
                list-style: inside;
                margin-top: 10px
                margin-left 10px
              }
            }
          }
          .cny-recharge-withdraw-record {
            .cny-recharge-title {
              height 40px
              line-height 40px
            }
            .select-container {
              margin-bottom 10px
            }
            .tabheader {
              background $color-global-text-dark-bg
            }
            .pagination-container {
              text-align center
              margin-top 10px
            }
            .cny-table {
              border: 1px solid $color-global-el-tb-border
              border-bottom none
            }
          }

          .withdraw-container {
            .withdraw-content {
              padding-left 20px
              height 200px
              background $color-finance-pay-bg
              display flex
              flex-direction column
              justify-content center
              align-items center
              .withdraw-input-container {
                display: flex
                justify-content space-between
                align-items center
                flex-wrap wrap
                height 120px /*.withdraw-content-item {
                  width 350px
                  height 35px
                  font-size $font-size-medium
                  color $color-global-text-light
                  .withdraw-input {
                    margin-left 24px
                    width 200px
                    height 35px
                    .validate-code {
                      color $color-global-blue
                      cursor pointer
                    }
                  }
                }*/

                .withdraw-input-area {
                  width 200px
                  height 35px
                }
              }

              .withdraw-confirm {
                width 150px

              }
              .el-input-group__append {
                cursor pointer
                width 72px
                text-align center
              }
            }
            .title {

            }
          }

        }

      }

    }
  }
</style>
