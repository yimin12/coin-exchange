<template>
  <div class="ucenter-wrap" v-wechat-title="$t('m.navheader.addressManagement')">
    <NavHeader activeTab="3"></NavHeader>
    <div class="ucenter-content">
        <div class="ucenter-row">
            <div class="ucenter-main no-sidebar">
                <div class="ucenter-header">
                    <h3 class="title">
                      {{$t('m.navheader.addressManagement')}}
                    </h3>
                    <div class="tips">
                        <el-button type="primary" class="add-address" @click="handleAddrOperation">
                          {{$t('m.addressManagement.addWithdrawAddr')}}
                        </el-button>
                    </div>
                </div>
                <div class="ucenter-body">
                  <div class="coinsList">
                    <div v-for="item in qbbListData" @click="handleCoinClick(item.id, item.name)"
                         :class="item.id == coinId?'coins-item-active coins-item':'coins-item' ">
                      <img :src="item.img">
                      <span>{{item.name}}</span>
                    </div>
                  </div>

                  <el-table
                    :data="wallAddressList"
                    style="width: 100%;margin-top:20px"
                    header-cell-class-name="address-tb-header-xxx"

                    class="address-tb"
                  >
                    <el-table-column
                      :label="$t('m.remark')"
                      prop="name"
                    >
                    </el-table-column>
                    <el-table-column
                      :label="$t('m.address')"
                      prop="addr"
                    >
                    </el-table-column>

                    <el-table-column
                      :label="$t('m.operate')"
                      width="300"
                    >
                      <template slot-scope="scope">
                        <!--<el-button size="mini" @click="modify(scope,0)">修改</el-button>-->
                        <el-button type="danger" size="mini" @click="delWallPromt(scope)">{{$t('m.delete')}}</el-button>
                      </template>
                    </el-table-column>
                  </el-table>

                </div>
            </div>
        </div>
    </div>
    <div class="ucenter-footer">
      <mFooter class="footer-bar"></mFooter>
    </div>

    <el-dialog
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      width="30%"
    >

      <div class="dialog-input-container">


        <el-form :model="withdrawForm" :rules="withdrawFormRules" ref="withdrawForm" label-width="110px">

          <!--<input type="text">-->
          <input type="password" class="poshidden">

          <el-form-item :label="$t('m.address')" prop="addr">
            <el-input v-model="withdrawForm.addr" :placeholder="$t('m.addressManagement.inputWithdrawAddr')" type="text" auto-complete="off"></el-input>
          </el-form-item>

          <el-form-item :label="$t('m.addressManagement.addressAbbreviation')" prop="mark">
            <el-input v-model="withdrawForm.mark" :placeholder="$t('m.addressManagement.addressAbbreviation')" type="text" auto-complete="off"></el-input>
          </el-form-item>

          <el-form-item :label="$t('m.payPassword')" prop="payPassword">
            <el-input v-model="withdrawForm.payPassword" :placeholder="$t('m.inputPayPassword')" type="password" auto-complete="off"></el-input>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="_handleAddAddr()" class="submit-addr-btn">{{$t('m.yes')}}</el-button>
          </el-form-item>

        </el-form>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import financeHeader from './finance-header'
  import NavHeader from 'components/nav-header/nav-header'
  import mFooter from 'components/m-footer/m-footer'
  import { financeApi } from 'api/finance'
  import { OK } from 'api/config'
  import md5 from 'js-md5'
  import { mapGetters } from 'vuex'
  export default {
    data(){
      return {
        withdrawForm     : {
          addr       : '',//钱包地址
          mark       : '',//地址简称
          payPassword: '',
        },
        withdrawFormRules: {
          addr       : [
            {required: true, message: this.$t('m.addressManagement.inputWalletAddress'), trigger: 'blur'},
          ],
          mark       : [
            {required: true, message: this.$t('m.addressManagement.inputAddressAbbreviation'), trigger: 'blur'},
          ],
          payPassword: [
            {required: true, message: this.$t('m.inputPayPassword'), trigger: 'blur'},
          ],
        },
        coinId           : 2, // 默认btc
        coinName         : 'BTC',
        dialogTitle      : "添加地址",

        dialogVisible  : false,
        qbbListData    : [],
        wallAddressList: []
      }
    },
    created(){
      console.log("页面创建");
    },
    mounted(){
      this._getCoinQbbList();
      if (this.$route.query.id != undefined && this.$route.query.name != undefined) {
        this.coinId = this.$route.query.id;
        this.coinName = this.$route.query.name;
        this.handleCoinClick(this.coinId, this.coinName);
        if(this.setPayPassword) {
          this.handleAddrOperation();
        }

      }

    },
    computed:{
      ...mapGetters(['token','setPayPassword'])
    },
    methods   : {
      handleAddrOperation(){
        if (!this.setPayPassword) {
          this.$confirm(this.$t('m.completeHint'), this.$t('m.prompt'), {
            confirmButtonText: this.$t('m.setting'),
            cancelButtonText : this.$t('m.no'),
            type             : 'warning'
          }).then(() => {
            this.$router.push("/usercenter/complete-userinfo");
            //modify-password-trading
          }).catch(() => {

          });

        }else{
          this.dialogVisible = true
        }
      },
      delWallPromt(scope){
        console.log("地址id:", scope.row.id);
        this.$prompt(this.$t('m.userCenter.plzFundPassword'), this.$t('m.prompt'), {
          confirmButtonText: this.$t('m.yes'),
          cancelButtonText : this.$t('m.no'),
          inputPattern     : /\S+/,
          inputErrorMessage: this.$t('m.userCenter.plzFundPassword'),
          inputType        : 'password'
        }).then(({value}) => {
          console.log("value", value);
          this.withdrawForm.payPassword = value
          this._handleDelAddr(scope.row.id)
          /*this._handleDelAddr(function () {


           },function (res) {

           });*/

        }).catch(() => {

        });
      },
      handleCoinClick(id, name){
        this.coinName = name
        console.log("点击item的id", id);
        this.coinId      = id;
        this.dialogTitle = this.$t('m.add')+" "+ this.coinName+" "+this.$t('m.address');

        financeApi.walletList(id, this.token).then(res => {
          this.wallAddressList = res.data
        }).catch((res)=>{

        })
      },
      _handleAddAddr(){

        this.$refs['withdrawForm'].validate((valid) => {
          if (valid) {
            financeApi.serverAddWallet(
              this.coinId,
              this.withdrawForm.mark,
              this.withdrawForm.addr,
              this.withdrawForm.payPassword,
              this.token
            ).then(res => {

              this.$refs.withdrawForm.resetFields()
              this.$message({
                message: "添加地址成功",
                type   : 'success'
              });
              this.dialogVisible = false

              if(this.$route.query.referrer != undefined) {
                this.$router.push({
                  path:this.$route.query.referrer,
                  query:{
                    index:this.$route.query.index,
                    to: this.$route.query.to
                  }
                });
              }else{
                this.handleCoinClick(this.coinId,this.coinName)
              }
            }).catch((res)=>{

              this.$message({
                message: res.errmsg,
                type   : 'error'
              });
            })

          } else {
            console.log("submit error");
          }

        })
      },

      _handleDelAddr(addrId){

        financeApi.serverdeleteWalletAddress(addrId, this.withdrawForm.payPassword, this.token).then(res => {
          this.$message({
            message: "删除成功",
            type   : 'success'
          });
          this.handleCoinClick(this.coinId,this.coinName)

        }).catch((res)=>{
          this.$message({
            message: res.errmsg,
            type   : 'error'
          });
        })
      },
      _getCoinQbbList(){

        financeApi.getBaseCoinList().then(res => {
          this.qbbListData = res.data;
          if(this.qbbListData.length > 0) {
            let coin = this.qbbListData[0];
            if (!this.$route.query.id && !this.$route.query.name) {
              this.coinId = coin.id;
              this.coinName = coin.name;
              this.handleCoinClick(coin.id, coin.name);
            }
          }
        }).catch((res)=>{

        });
      }

    },
    components: {
      financeHeader,
      NavHeader,
      mFooter
    }
    ,
  }
</script>
<style lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/usercenter"
  .el-table__header-wrapper{
    .address-tb-header-xxx{
      background #F0F0F0
      padding 3px 5px
    }
  }
  .poshidden{
    position: absolute
    z-index: -1
  }
</style>

<style scoped lang="stylus" rel="stylesheet/stylus">
 .ucenter-wrap{
    .ucenter-main{
      .coinsList {
        display flex
        justify-content flex-start
        align-items center
        flex-wrap: wrap
        .coins-item {
          display: flex
          align-items center
          justify-content center
          width 80px
          height 30px
          border: 1px solid #aaabb1
          cursor pointer
          margin 5px
          border-radius 6px
          text-algin center
          overflow hidden
          white-space nowrap
          text-overflow ellipsis
          &.coins-item-active,
          &:hover {
            background #7392FF
            border-color #7392FF
            color #fff
          }

          img {
            width 16px
            height 16px
            margin-right 3px
          }
        }
      }
      .address-tb::before{
        background-color #fff
      }
      .add-address{
        background #7392FF
        border-color #7392FF
        &:hover{
          opacity .8
        }
      }
    }
 }
</style>
