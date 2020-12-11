<template>
  <div class="ucenter-wrap finance" v-wechat-title="$t('m.navheader.accountAssets')">
    <NavHeader activeTab="3"></NavHeader>
    <div class="ucenter-content">
      <div class="ucenter-row">
        <div class="ucenter-main no-sidebar">
          <div class="ucenter-header">
            <h3 class="title">
              <span>
              {{$t('m.accountAssets.assets')}}
              </span>
              <div class="opt">
                {{$t('m.accountAssets.hideSmallAssets')}}
                <toggle-button v-model="toggleBtn" color="#5BBC51"
                               :labels="{checked: 'on', unchecked: 'off'}"></toggle-button>
              </div>

            </h3>

            <div class="search-coin">
              <form action="" autocomplete="off">
                <el-input
                  :placeholder="$t('m.accountAssets.inputCoinName')"
                  v-model="searchCoin"
                  size="medium"
                  class="search-coin-input"
                  clearable
                  auto-complete="off"
                >
                  <i slot="append" class="el-icon-search"></i>
                </el-input>
              </form>


            </div>
          </div>
          <div class="ucenter-body">
            <div class="finance-assets">
              <div class="finance-total">
                <!--<span>{{$t('m.accountAssets.currentAmount')}}：</span>-->
                <!--<em>$ {{waihuiAssetsData.amountUs}}</em>-->
                <!--<em>/</em>-->
                <em>￥{{waihuiAssetsData.amount}}</em>
              </div>
              <div class="finance-notice">
                {{$t('m.accountAssets.accountTips')}}
              </div>
            </div>
            <div class="assets-list">

              <el-table
                :data="assetsTbData"
                ref="assetsTb"
                :row-key="getRowKeys"
                :expand-row-keys="expandList"
                style="width: 100%"
                header-cell-class-name="assets-tb-header"
                :default-sort="{prop: 'totalNum', order: 'descending'}"
                class="assets-tb">

                <el-table-column type="expand">
                  <template slot-scope="props">
                    <div class="assets-recharge-withdraw" v-if="currentExpandType==0">
                      <div class="recharge-withdraw-left">
                        <p class="recharge-withdraw-title">{{$t('m.accountAssets.recharge')}}</p>
                        <div v-if="currentCoinName.toLowerCase() === 'xrp'" style="font-size: 14px">
                          <p style="margin-bottom: 15px">{{$t('m.accountAssets.account')}}:
                            {{rechargeAddr.split('|')[0]}}</p>
                          <p>{{$t('m.remark')}}: {{rechargeAddr.split('|')[1]}}</p>
                        </div>
                        <div class="recharge-container" v-else>
                          <div class="recharge-addr">
                            <p class="recharge-addr-title">
                              {{$t('m.accountAssets.walletAddress')}}</p>
                            <p class="recharge-addr-content" v-model="rechargeAddr" ref="qrcode">
                              {{rechargeAddr}}</p>
                            <el-button v-clipboard:copy="rechargeAddr"
                                       v-clipboard:success="onCopy"
                                       v-clipboard:error="onError"
                                       :plain="true"
                                       size="mini" class="recharge-btn-container"
                            >{{$t('m.accountAssets.copy')}}
                            </el-button>

                          </div>
                          <div class="recharge-qrcode" ref="qrcodeImg">
                          </div>
                        </div>
                        <p class="recharge-tips"> {{$t('m.accountAssets.rechargeTips1')}}{{currentCoinName}}
                          {{$t('m.accountAssets.rechargeTips2')}} {{currentCoinName}}
                          {{$t('m.accountAssets.rechargeTips3')}}
                        </p>
                      </div>
                      <div class="recharge-withdraw-right">
                        <p class="recharge-withdraw-title"> {{$t('m.accountAssets.rechargeRecord')}}
                          <router-link tag="span" :to="rechargeMore+currentCoinId"
                                       class="more-record">
                            {{$t('m.accountAssets.more')}}
                          </router-link>
                        </p>

                        <el-table
                          :data="inWalletRecord"
                          key="inWalletRecord"
                          style="width: 100%"
                          header-cell-class-name="outwall-tb-header"
                          height="240"
                          class="recharge-withdraw-right-tb"
                        >
                          <el-table-column
                            :label="$t('m.accountAssets.rechargeTime')"
                            prop="created"
                          >
                          </el-table-column>
                          <el-table-column
                            :label="$t('m.accountAssets.rechargeAddr')"
                            prop="addr"
                            :formatter="formatAddr"
                            width="120"
                          >
                          </el-table-column>
                          <el-table-column
                            :label="$t('m.accountAssets.rechargeNum')"
                            prop="amount"
                          >
                          </el-table-column>
                          <!--                              <el-table-column
                                                          :label="$t('m.accountAssets.rechargeFee')"
                                                          prop="fee"
                                                          width="110"
                                                        >
                                                        </el-table-column>-->
                          <el-table-column
                            :label="$t('m.accountAssets.status')"
                            prop="status"
                          >
                            <template slot-scope="scope">
                              <span>{{formatStatus(scope.row, 1)}} </span>
                            </template>
                          </el-table-column>
                        </el-table>

                      </div>
                    </div>

                    <div class="assets-recharge-withdraw" v-else>
                      <div class="recharge-withdraw-left">
                        <p class="recharge-withdraw-title">{{$t('m.accountAssets.withdraw')}}</p>
                        <p class="recharge-withdraw-left-input-area">
                              <span
                                class="recharge-withdraw-left-label">{{$t('m.accountAssets.withdrawAddr')}}
                                </span>
                          <el-select v-model="withdrawAddrSelect"
                                     :placeholder="$t('m.accountAssets.select')"
                                     class="withdraw-address-select"
                                     size="medium">
                            <el-option
                              v-for="item in withdrawAddrSelectList"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value">
                            </el-option>
                          </el-select>

                          <el-button type="primary" size="medium" class="withdraw-address-add"
                                     @click="handleAddAddr(props.$index)">{{$t('m.accountAssets.add')}}
                          </el-button>

                        </p>
                        <p class="recharge-withdraw-left-input-area">

                          <span class="recharge-withdraw-left-label">{{
                            $t('m.accountAssets.withdrawNum')}}
                          </span>

                          <el-input
                            placeholder="0"
                            v-model="withdrawNum"
                            size="medium"
                            class="withdraw-address-input"
                          >
                          </el-input>
                        </p>

                        <div style="padding-left: 72px;" v-show="!withdrawNumError">
                          <p style="color: #7392ff;">
                            {{$t('m.accountAssets.fee')}} {{feeNum}} {{currentCoinName}}{{withdrawNumErrorMsg}}
                          </p>
                        </div>

                        <div style="padding-left: 72px;" v-show="withdrawNumError">
                          <p style="color:red;">
                            {{withdrawNumErrorMsg}}
                          </p>
                        </div>

                        <p class="recharge-withdraw-left-input-area">

                          <span
                            class="recharge-withdraw-left-label">{{$t('m.accountAssets.mobileValidate')}}</span>
                          <el-input
                            :placeholder="$t('m.accountAssets.inputValidateCode')"
                            v-model="phoneCode"
                            size="medium"
                            class="withdraw-address-input"
                            :maxlength="6"
                          >
                            <template slot="append">
                              <validateCode :mobile="mobile" type="WITHDRAW_APPLY"
                                            tag="span" :countryCode="this.countryCode"></validateCode>
                            </template>
                          </el-input>

                        </p>

                        <p class="recharge-withdraw-left-input-area">

                          <span
                            class="recharge-withdraw-left-label">{{$t('m.accountAssets.payPassword')}} </span>
                          <!--<span class="recharge-withdraw-left-label">Mobile Validate</span>-->
                          <el-input
                            :placeholder="$t('m.accountAssets.inputPayPassword')"
                            v-model="transactionPWD"
                            size="medium"
                            class="withdraw-address-input"
                            type="password"
                            auto-complete="off"></el-input>
                        </p>

                        <div class="btn-container">
                          <el-button size="mini"
                                     class="withdraw-btn"
                                     type="primary"
                                     @click="submitWithdraw">
                            {{$t('m.accountAssets.submit')}}
                          </el-button>
                        </div>

                      </div>
                      <div class="recharge-withdraw-right">
                        <p class="recharge-withdraw-title">{{$t('m.accountAssets.withdrawRecord')}}
                          <router-link tag="span" :to="withdrawMore+currentCoinId"
                                       class="more-record">
                            {{$t('m.accountAssets.more')}}
                          </router-link>
                        </p>

                        <el-table
                          :data="outWalletRecord"
                          key="outWalletRecord"
                          style="width: 100%"
                          header-cell-class-name="outwall-tb-header"
                          height="240"
                          class="recharge-withdraw-right-tb"
                        >
                          <el-table-column
                            :label="$t('m.accountAssets.withdrawTime')"
                            prop="created"
                          >
                          </el-table-column>
                          <el-table-column
                            :label="$t('m.accountAssets.withdrawAddr')"
                            prop="addr"
                            width="120"
                            :formatter="formatAddr"
                          >
                          </el-table-column>
                          <el-table-column
                            :label="$t('m.accountAssets.withdrawNum')"
                            prop="num"
                          >
                          </el-table-column>
                          <el-table-column
                            :label="$t('m.accountAssets.withdrawFee')"
                            prop="fee"
                          >
                          </el-table-column>
                          <el-table-column
                            :label="$t('m.accountAssets.withdrawMum')"
                            prop="mum"
                            width="130"
                          >
                          </el-table-column>

                          <el-table-column
                            :label="$t('m.accountAssets.withdrawStatus')"
                            prop="status"
                          >
                            <template slot-scope="scope">
                                  <span>{{formatStatus(scope.row, 2)}}
                                    <el-tag type="danger"
                                            v-if="scope.row.remark&&scope.row.status==2">{{scope.row.remark}}</el-tag>
                                  </span>
                            </template>
                          </el-table-column>
                        </el-table>
                      </div>
                    </div>
                  </template>

                </el-table-column>
                <el-table-column
                  :label="$t('m.accountAssets.coinName')"
                  width="100"
                >
                  <template slot-scope="scope">
                    <div class="coin-img-wapper">
                      <img :src="scope.row.coinImgUrl" class="coin-img" alt="">
                      <span>{{ scope.row.coinName.toUpperCase() }}</span>
                    </div>
                  </template>

                </el-table-column>

                <el-table-column
                  prop="carryingAmount"
                  :label="$t('m.accountAssets.carryingAmount')"
                  align="center"
                >
                </el-table-column>
                <el-table-column
                  :label="$t('m.accountAssets.freezeAmount')"
                  prop="freezeAmount"
                  align="center"
                  sortable>
                </el-table-column>

                <el-table-column
                  prop="balanceAmount"
                  :label="$t('m.accountAssets.balanceAmount')"
                  sortable
                  align="center"
                >
                </el-table-column>
                <el-table-column
                  :label="$t('m.accountAssets.operation')"
                  prop="operation"
                  align="center"
                  width="250"
                >
                  <template slot-scope="scope">
                    <el-button size="mini" @click="expandRow(scope,0)"
                               :disabled="scope.row.rechargeFlag==0">
                      {{$t('m.accountAssets.recharge')}}
                    </el-button>
                    <el-button size="mini"
                               @click="expandRow(scope,1)"
                               :disabled="scope.row.withdrawFlag==0">
                      {{$t('m.accountAssets.withdraw')}}
                    </el-button>

                    <!--<el-button size="mini" @click="handleRelease(scope.row.coinId)"-->
                               <!--v-show="scope.row.coinId === freezingData.coinId"-->
                              <!--:disabled="freezingData.freeze<=0">解冻-->
                    <!--</el-button>-->
                  </template>
                </el-table-column>
                <!--资金列表-->
              </el-table>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="ucenter-footer">
      <mFooter class="footer-bar"></mFooter>
    </div>


    <!--<el-dialog-->
      <!--:title="$t('m.prompt')"-->
      <!--:visible.sync="dialogVisible"-->
      <!--width="30%">-->
      <!--<p>-->
        <!--<el-col :span="12">{{$t("m.accountAssets.poolAmount")}}</el-col>-->
        <!--<el-col :span="12">{{freezingData.amount}}</el-col>-->
      <!--</p>-->
      <!--<el-row>-->
        <!--<el-col :span="12">{{$t("m.accountAssets.poolThawed")}}</el-col>-->

        <!--<el-col :span="12">{{freezingData.thawed}}</el-col>-->
      <!--</el-row>-->
      <!--<el-row>-->
        <!--<el-col :span="12">{{$t("m.accountAssets.poolFreeze")}}</el-col>-->
        <!--<el-col :span="12">{{freezingData.freeze}}</el-col>-->
      <!--</el-row>-->
      <!--<el-row>-->
        <!--<el-col :span="12">{{$t("m.accountAssets.poolCanDefrost")}}</el-col>-->
        <!--<el-col :span="12">{{freezingData.canDefrost}}</el-col>-->
      <!--</el-row>-->
      <!--<span slot="footer" class="dialog-footer">-->
        <!--<el-button @click="dialogVisible = false">{{$t("m.no")}}</el-button>-->
        <!--<el-button type="primary" @click="submitRelease">{{$t("m.yes")}}</el-button>-->
      <!--</span>-->
    <!--</el-dialog>-->
  </div>
</template>
<script>
  import ToggleButton from 'vue-js-toggle-button'
  import Vue from 'vue'
  import NavHeader from 'components/nav-header/nav-header'
  import mFooter from 'components/m-footer/m-footer'
  import VueClipboard from 'vue-clipboard2'
  import {util} from 'common/js/util'
  import dom from 'common/js/dom'
  import {financeApi} from 'api/finance'
  import validateCode from 'base/validate-code/validate-code'

  import {commonApi} from 'api/common'
  import {OK} from 'api/config'
  import QRCode from 'qrcodejs2'
  import {cookieUtil} from 'common/js/cookieUtil'
  import md5 from 'js-md5'
  import {mapGetters} from 'vuex'

  Vue.use(VueClipboard)
  Vue.use(ToggleButton)
  export default {
    data() {
      return {
        rechargeMore: '/order/recharge-record/',
        withdrawMore: '/order/withdraw-record/',
        rows: 5, // 交易记录每页显示条数
        page: 1,
        inWalletRecordTotal: 0,
        outWalletRecordTotal: 0,
        // 隐藏小额资产
        toggleBtn: false,
        // 美金余额
        amountUs: 0,
        amount: 0,
        searchCoin: '',
        assetsTbData: [],
        assetsTbDataOrigin: [],
        //存储展开状态
        expandList: [],
        // 0 充值 1 提现
        currentExpandType: 0,
        //当前展开的币种
        currentCoinId: -1,
        currentCoinRow: {},
        rechargeAddr: '',
//        widthdrawAddr         : '',
        outWalletRecord: [],
        inWalletRecord: [],
        feeNum: 0,
        // 展开币种名称
        currentCoinName: '',
        withdrawAddress: '',
        withdrawNum: '',
        withdrawNumError: false,
        withdrawNumErrorMsg: '',
        phoneCode: '',
        transactionPWD: '',
        // 提现下拉框
        withdrawAddrSelect: '',
        withdrawAddrSelectList: [],

        tabActiveName: 'first',
        waihuiAssetsData: [],
        // freezingAmount:10,
        freezingData: {},
        dialogVisible: false
      }
    },
    created() {
      // this.bus.$on("isLogin", this.loginFinishFunc)
    },
    mounted() {
      if (this.token) {
        this.subscribeAssets()

        // this._getRewardInfo()

      }
    },
    components: {
      NavHeader,
      mFooter,
      validateCode
    },
    computed: {
      ...mapGetters(['mobile', 'username', 'token', 'lang', 'isAuth', 'countryCode'])
    },
    watch: {
      searchCoin: function (val) {
        let valUpper = val.toUpperCase()
        if (this.toggleBtn) {
          this.assetsTbData = this.assetsTbDataOrigin.filter(item => {
            return item.coinName.toUpperCase().indexOf(valUpper) != -1 && item.carryingAmount > 0
          })
        } else {
          this.assetsTbData = this.assetsTbDataOrigin.filter(item => {
            return item.coinName.toUpperCase().indexOf(valUpper) != -1
          })
        }
      },
      toggleBtn: function (val) {
        let valUpper = this.searchCoin.toUpperCase()
        console.log(valUpper);
        if (val) {
          this.assetsTbData = this.assetsTbDataOrigin.filter(item => {
            return item.coinName.toUpperCase().indexOf(valUpper) != -1 && item.carryingAmount > 0
          })
        } else {
          this.assetsTbData = this.assetsTbDataOrigin.filter(item => {
            return item.coinName.toUpperCase().indexOf(valUpper) != -1
          })
        }
      },
      // 提币 --> 转出手续费
      withdrawNum(val) {
        const patrn = /^\d+(\.\d+)?$/;
        if (val == '') {
          this.withdrawNumError = false;
          this.withdrawNumErrorMsg = '';
          return;
        }
        if (!patrn.test(val)) {
          this.withdrawNumError = true;
          this.withdrawNumErrorMsg = '转出数量错误';
          return;
        }
        if (val < 0) {
          this.withdrawNumError = true;
          this.withdrawNumErrorMsg = '不支持输入负数';
          return;
        }
        const currentRow = this.currentCoinRow;
        const {balanceAmount = 99999999} = currentRow;
        const feeNum = this.feeNum;
        const maxAmount = balanceAmount;
        if (val < feeNum) {
          this.withdrawNumError = true;
          this.withdrawNumErrorMsg = `最小转出数量：${feeNum}`;
          return;
        }
        if (val > maxAmount) {
          this.withdrawNumError = true;
          this.withdrawNumErrorMsg = `余额不足，最多可提：${maxAmount}`;
          return;
        }
        const getDigits = (val) => {
          const str = val + '';
          if (str.indexOf('.') === -1) {
            return 0;
          }
          return str.length - str.indexOf('.') - 1;
        };
        let digitsFee = getDigits(feeNum);
        let digitsVal = getDigits(val);
        let digits = digitsFee < digitsVal ? digitsVal : digitsFee;
        if (digits > 8) {
          this.withdrawNumError = true;
          this.withdrawNumErrorMsg = `转出数量错误`;
          return;
        }
        this.withdrawNumError = false;
        this.withdrawNumErrorMsg = `，实际到账数量：${(val - feeNum).toFixed(digits)}`;
      }
    },
    methods: {
      // async _getRewardInfo() {
      //   let res = await financeApi.getRewardInfo()
      //   this.freezingData = res.data
      // },
      // async _unfreezeReward() {
      //   let res = await financeApi.unfreezeReward()
      //   if(res.errcode === 0){
      //     this.$message({
      //       message: that.$t('m.accountAssets.unfreezeSuccess'),
      //       type: 'success'
      //     });
      //   }
      // },
      // submitRelease(){
      //   this._unfreezeReward()
      // },
      handleRelease() {
        this.dialogVisible = true


        /*当前可解冻金额为${this.freezingAmount},*/
        // this.$confirm(`是否解冻`, '提示', {
        //   confirmButtonText: '确定',
        //   cancelButtonText: '取消',
        //   type: 'warning'
        // }).then(() => {
        //   this._unfreezeReward()
        // })
      },
      submitWithdraw() {
        let that = this

        if (this.withdrawAddrSelect == '') {
          this.$message({
            message: that.$t('m.accountAssets.inputWithDrawAddr'),
            type: 'error'
          });
          return
        }
        if (this.phoneCode == '') {
          this.$message({
            message: that.$t('m.accountAssets.plzValidationCode'),
            type: 'error'
          });
          return
        }
        if (this.withdrawNum == '' || !this.withdrawNum) {
          this.$message({
            message: that.$t('m.accountAssets.inputWithDrawNum'),
            type: 'error'
          });
          return
        }
        if (this.transactionPWD == '') {

          this.$message({
            message: that.$t('m.accountAssets.inputPayPassword'),
            type: 'error'
          });
          return
        } else {
          let passwordReg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$/;
          if (!(passwordReg.test(this.transactionPWD))) {
            this.$message({
              message: that.$t('m.userCenter.passwordFormatError'),
              type: 'error'
            });
          }
        }

        financeApi.serverWithdraw(
          this.withdrawAddrSelect,
          this.withdrawNum,
          this.currentCoinId,
          this.transactionPWD,
          this.phoneCode,
          this.token
        ).then(() => {
          this.$alert(that.$t('m.accountAssets.withDrawSuccess'), that.$t('m.prompt'), {
            confirmButtonText: that.$t('m.yes'),
            callback: action => {
              this.withdrawAddress = '';
              this.withdrawNum = '';
              this.phoneCode = '';
              this.transactionPWD = '';
            }
          });
          this._outWalletRecord(this.rows, this.page, this.currentCoinId)
          // 提币后刷新账户资产页面
          this.subscribeAssets()
        }).catch(() => {
          this.$message({
            message: res.errmsg,
            type: 'error'
          });
        })

      },
      // 增加提现地址
      handleAddAddr(index) {
        let scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
        this.$router.push({
          path: "/finance/address-manager/",
          query: {
            id: this.currentCoinId,
            name: this.currentCoinName,
            index: index,
            to: scrollTop,
            referrer: '/finance'
          }
        })
      },
      handlePageChange(currentPage) {
        console.log("handlePageChange:", currentPage);
        console.log("currentCoinId:", this.currentCoinId);
        this.page = currentPage
        if (this.currentExpandType == 0) {
          this._inWalletRecord(this.rows, this.page, this.currentCoinId)
        } else {
          this._outWalletRecord(this.rows, this.page, this.currentCoinId)
        }
      },
      onCopy: function (e) {
        this.$message({
          message: this.$t('m.copySuccess'),
          type: 'success'
        });
      },
      onError: function (e) {
        this.$message(this.$t('m.copyError'));
      },
      // 获取row的key值
      getRowKeys(row) {
        return row.coinId;
      },
      goToSeniorAuth() {
        this.$alert(this.$t('m.userCenter.plzSeniorAuth'), this.$t('m.prompt'), {
          confirmButtonText: this.$t('m.yes'),
          type: 'warning',
          callback: action => {
            if (action == "confirm") {
              this.$router.push("/usercenter/modify-id")
            }

          }
        });
      },
      expandRow(scope, type) {
        console.log('dd', scope)
        if (type === 1 && this.isAuth < 1) {
          this.goToSeniorAuth()
          return
        }
        this.withdrawAddrSelect = ''
        this.currentCoinName = scope.row.coinName
        let coinId = scope.row.coinId

        if (type == 0) {
          this._inWalletRecord(this.rows, this.page, coinId)
          // 获取钱包列表
          this._getRechargeAddress(coinId)
        } else {
          this._outWalletRecord(this.rows, this.page, coinId)
          this._wallList(coinId)
          // // 获取手机号
          // this._getRechargeAddress(coinId)
        }

        if (this.expandList.indexOf(scope.row.coinId) != -1) {
          if (coinId == this.currentCoinId && this.currentExpandType == type) {
            this.expandList.splice(this.expandList.findIndex(v => v == coinId), 1);
          } else {

          }
          // 24
        } else {
          this.expandList = []
          this.expandList.push(coinId)
        }

        this.currentCoinId = coinId
        this.currentCoinRow = scope.row;
        this.currentExpandType = type
        this.feeNum = scope.row.minFeeNum

        // 清除二维码和地址 防止因为网络原因 导致显示之前的地址
        let ref = this.$refs.qrcodeImg
        // todo 暂时只清除掉了地址，二维码还没有清除 'childNodes' of undefined
        // dom 还没渲染出来

        this.rechargeAddr = ''
        if (ref != null) {
          let childs = ref.childNodes
          for (var i = 0; i < childs.length; i++) {
            ref.removeChild(childs[i]);
          }
        }

//        this.$refs.assetsTb.toggleRowExpansion(scope.row,true)

//        this.$refs.assetsTb.toggleRowExpansion(scope.row,false)

      },

      toggleRowExpansion(row, selected) {
        console.log("切换：", row, selected);
      },
      formatDate(row, column) {
        return util.date2DateTimeString2(new Date(row.createTime))
      },
      formatAddr(row, column) {
        return util.coinAddrFormat(row.address)
      },
      formatStatus(row, type) {
        if (type == 1) {
          return util.rechargeCoinStatusFormat(row.confirm, row.status)
        } else {
          return util.statusFormat(row.status)
        }
      },
      _wallList(id) {
        financeApi.walletList(id, this.token).then(res => {
          this.withdrawAddrSelectList = []
          for (let i in res.data) {
            let item = res.data[i]
            let newItem = {}
            newItem.value = item.id
            newItem.label = item.name
            this.withdrawAddrSelectList.push(newItem)
          }
        }).catch(() => {
        })
      },
      _outWalletRecord(rows, page, coinId) {
        financeApi.serverOutWalletRecord(rows, page, coinId, this.token).then(res => {
          let data = res.data;
          this.outWalletRecord = data.records;
          this.outWalletRecordTotal = data.total;
        })
      },
      _inWalletRecord(rows, page, coinId) {
        financeApi.serverInWalletRecord(rows, page, coinId, this.token).then(res => {
          let data = res.data;
          this.inWalletRecord = data.records;
          this.inWalletRecordTotal = data.total;
        }).catch(() => {
        })
      },
      async _getRechargeAddress(coinId) {
        let res = await financeApi.serverRechargeAddress(coinId, this.token);
        this.rechargeAddr = res.data
        let addr = this.rechargeAddr
        let ref = this.$refs.qrcodeImg
        let qrcode = new QRCode(ref, {
          text: addr,
          width: 128,
          height: 128,
          colorDark: "#000000",
          colorLight: "#ffffff",
          correctLevel: QRCode.CorrectLevel.H
        });

        let childs = ref.childNodes
        for (var i = 0; i < childs.length; i++) {
          ref.removeChild(childs[i]);
        }
      },

      //页码变更
      handleCurrentChange: function (val) {
        this.currentPage = val;
//      this.loadData(this.criteria, this.currentPage, this.pagesize);
      },

      /**
       * 实时资金账户
       */
      subscribeAssets() {
        // if (this.stompClient_forex && this.stompClient_forex.connected) {
        //   if (this.fundsSubscribe && this.fundsSubscribe.id) {
        //     this.fundsSubscribe.unsubscribe()
        //   }
        //   this.stompClient_forex.send("/app/assets", {usessionId: this.usessionId}, JSON.stringify({}));
        //   this.fundsSubscribe = this.stompClient_forex.subscribe("/user/" + this.usessionId + "/assets/subscribe"
        //     , (res) => {
        //
        //       let result = JSON.parse(res.body)
        //       console.log("实时资金账户socket：", result)
        //
        //       this.waihuiAssetsData = result
        //
        //       this.assetsTbData = this.waihuiAssetsData.assertList
        //
        //       //备份原来数据
        //       this.assetsTbDataOrigin = this.assetsTbData
        //     });
        // } else {
        //   setTimeout(() => {
        //     this.subscribeAssets()
        //   }, 500)
        // }

        financeApi.serverAccount(this.token).then(res => {
          this.waihuiAssetsData = res.data
          this.assetsTbData = this.waihuiAssetsData.assertList
          //备份原来数据
          this.assetsTbDataOrigin = this.assetsTbData;
          console.log("", typeof this.$route.query.index)
          if (typeof this.$route.query.index != "undefined") {
            let data = {row: this.assetsTbData[this.$route.query.index]};
            this.expandRow(data, 1);
            this.$nextTick(() => {
              let to = this.$route.query.to;
              document.documentElement.scrollTop = document.body.scrollTop = to
            })
          }
        }).catch(() => {

        })
      }
    }

  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/usercenter"
  .finance {
    .el-input__inner {
      border 1px solid #AAABB1
    }
    .search-coin {
      .el-input__inner {
        border-right none
      }
    }
    .el-input__inner:focus {
      border-color #AAABB1
      outline 0
    }
    .el-input-group__append {
      background-color #fff
      border 1px solid #AAABB1
      border-left none
    }
    .assets-list {
      .el-table__header {
        table-layout auto
        width 100% !important
      }
      .assets-tb-header {
        background-color #F0F0F0
        padding 2px 0
        border-bottom none
      }
    }
    .recharge-container {
      .recharge-qrcode {
        img {
          width 165px
          height 165px
          margin 0 auto
        }
      }
    }
    .el-table__expand-column {
      padding 0
      .el-table__expand-icon {
        display none
      }
    }
    .el-table__expanded-cell {
      padding 20px
    }
  }
</style>

<style scoped lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/variable"
  .ucenter-wrap {
    .ucenter-main {
      .ucenter-header {
        .title {
          display flex
          justify-content space-around
          span {
            margin-right 10px
          }
          .opt {
            font-size 14px
            color #AAABB1
          }
        }
      }
      .ucenter-body {
        .finance-assets {
          display flex
          height 40px
          align-items center
          justify-content space-between
          .finance-total {
            flex 3
            span {
              color #AAABB1
              font-size 14px
            }

            em {
              color #7392FF
              font-size 24px
              font-style normal
            }

          }
          .finance-notice {
            flex 2

            color #AAABB1
            font-size 14px
          }
        }
      }
    }
  }

  .assets-list {
    margin-top 10px
    margin-bottom 80px

    .el-table__expanded-cell {
      padding 15px
    }
    .el-table__row {
      font-size $font-size-small
    }
    .el-table__expand-icon {
      display none
    }

    .assets-tb {
      border: 1px solid $color-global-el-tb-border
      border-bottom none
      font-size 12px
    }
    .assets-recharge-withdraw {
      display flex
      justify-content space-between
      height 340px
      .recharge-withdraw-title {
        font-size $font-size-medium-x
        color $color-global-tb-header-text
        margin-bottom: 19px;
        .more-record {
          float: right
          cursor pointer
        }
      }
      .recharge-withdraw-left {
        padding 10px 0
        width 350px
        background #fff
        .recharge-container {
          display flex
          justify-content space-between
          .recharge-addr {
            width 165px
            height: 165px
            background #DAEDF7
            border: 1px solid #BEE8F0
            color #34708D
            padding 14px 10px
            .recharge-addr-title {
              font-size $font-size-medium
            }
            .recharge-addr-content {
              font-size $font-size-medium-x
              margin-top 14px
              word-wrap: break-word;
              word-break: normal;
            }
          }
          .recharge-qrcode {
            text-align center
            width 165px
            height 165px
          }

        }
        .recharge-btn-container {
          margin-top 10px
        }
        .recharge-tips {
          margin-top 31px
          font-size $font-size-medium
          color $color-global-text-light
        }
        .recharge-withdraw-left-input-area {
          width 330px
          height 50px
          line-height: 50px
          .recharge-withdraw-left-label {
            font-size $font-size-medium
            color $color-global-text-dark
            display: inline-block;
            width: 70px;
          }
          .validate-code {
            width 60px
            text-align center
          }
          .get-code {
            cursor pointer
            color $color-global-get-code
            font-size $font-size-small
            text-align center
          }
          .el-input-group__append {
            cursor pointer
            width 101px
            text-align center
          }
          .withdraw-address-select {
            width 190px
            height 36px
          }
          .withdraw-address-add {
            width 60px
            padding: 10px 8px;
          }
          .withdraw-address-input {
            width 255px
            height 36px
          }

        }
        .btn-container {
          text-align center
          padding-left 50px
          .withdraw-btn {
            width 255px
            height 32px
            margin-top 20px
            &:disabled {
              opacity 0.6
            }
          }
        }
      }

      .recharge-withdraw-right {
        padding 10px 0
        background #fff
        flex 1
        .recharge-withdraw-right-tb {

        }

        margin-left 15px

        .pagination-container {
          text-align center
          margin-top 10px
        }

      }

    }
  }

  .coin-img-wapper {
    position: relative;
    .coin-img {
      width: 20px
      height: 20px
      vertical-align: middle;
      display: inline-block;
    }
    span {
      position: absolute;
      display: inline-block;
      padding-left: 10px;
      top: 0px;
      line-height: 25px;
    }
  }

</style>

<style lang="stylus">
  .assets-list {

    .el-tabs--card > .el-tabs__header .el-tabs__item {
      height: 35px;
      line-height: 35px;
    }
    .el-tabs--card > .el-tabs__header .el-tabs__item:hover,
    .el-tabs--card > .el-tabs__header .el-tabs__item.is-active {
      color #7392FF
    }

    .el-table__empty-block {
      width 100% !important
      border-bottom 1px solid #ebeef5
    }
  }
</style>
