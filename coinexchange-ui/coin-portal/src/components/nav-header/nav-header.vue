<template>
  <div class="m-header" ref="myHeader">
    <div class="nav-container">
      <!--<a href="/"><img class="logo" src="../../assets/header/logo.png" alt=""></a>-->
      <ul class="nav-container-left">
        <router-link tag="li" to="/home">
          <!--<span class="tab-link logo" >LOGO</span>-->
          <img src="../../assets/home/logon.png" alt="">
        </router-link>

				<router-link tag="li" to="/home">
				  <span :class="activeTab === '2' ? 'tab-link active-tab' : 'tab-link'">{{$t('m.navheader.homeMenu')}}</span>
				</router-link>

        <!--币币交易-->
        <router-link tag="li" to="/trade">
          <span :class="activeTab === '2' ? 'tab-link active-tab' : 'tab-link'">{{$t('m.navheader.trade')}}</span>
        </router-link>

        <!--<router-link tag="li" to="/exchange">-->
          <!--<span :class="activeTab === '7' ? 'tab-link active-tab' : 'tab-link'">{{$t('m.navheader.exchange')}}</span>-->
        <!--</router-link>-->
        <!--c2c交易-->
        <router-link tag="li" to="/c2c">
          <span :class="activeTab === '8' ? 'tab-link active-tab' : 'tab-link'">{{$t('m.navheader.c2c')}}</span>
        </router-link>

        <!--帮助中心-->
        <!--<router-link tag="li" to="/s/help">-->
          <!--<span :class="activeTab == 8 ? 'tab-link active-tab' : 'tab-link'">{{$t('m.footer.help')}}</span>-->
        <!--</router-link>-->


        <!--更成非链接标签-->
        <!--<div class="menuitem">-->
          <!--<el-dropdown :show-timeout="0" >-->
            <!--<span class="el-dropdown-link" :class="activeTab === '3' ? 'active-tab' : ''">-->
              <!--{{$t('m.navheader.assets')}}<i class="el-icon-arrow-down nav-icon-right"></i>-->
            <!--</span>-->
            <!--<el-dropdown-menu slot="dropdown">-->
              <!--<el-dropdown-item>-->
                <!--<router-link tag="li" to="/finance">-->
                  <!--{{$t('m.navheader.accountAssets')}}-->
                <!--</router-link>-->
              <!--</el-dropdown-item>-->
              <!--&lt;!&ndash;<el-dropdown-item>&ndash;&gt;-->
              <!--&lt;!&ndash;<router-link tag="li" to="/finance/btcx-to-cny">&ndash;&gt;-->
              <!--&lt;!&ndash;BTCX兑换&ndash;&gt;-->
              <!--&lt;!&ndash;</router-link>&ndash;&gt;-->
              <!--&lt;!&ndash;</el-dropdown-item>&ndash;&gt;-->
              <!--<el-dropdown-item>-->
                <!--<router-link tag="li" to="/finance/address-manager">-->
                  <!--{{$t('m.navheader.addressManagement')}}-->
                <!--</router-link>-->
              <!--</el-dropdown-item>-->
        <!--&lt;!&ndash;投票上币&ndash;&gt;-->
        <!--&lt;!&ndash;<router-link tag="li" to="">&ndash;&gt;-->
          <!--&lt;!&ndash;<span :class="this.activeTab == 8 ? 'tab-link active-tab' : 'tab-link'">{{$t('m.navheader.token')}}</span>&ndash;&gt;-->
        <!--&lt;!&ndash;</router-link>&ndash;&gt;-->





        <!--&lt;!&ndash;<div class="menuitem">-->
          <!--<el-dropdown :show-timeout="0" >-->
            <!--<span class="el-dropdown-link" :class="this.activeTab == 4 ? 'active-tab' : ''">-->
              <!--{{$t('m.navheader.order')}}<i class="el-icon-arrow-down nav-icon-right"></i>-->
            <!--</span>-->
            <!--<el-dropdown-menu slot="dropdown">-->
              <!--<el-dropdown-item>-->
                <!--<router-link tag="li" to="/order/entrust-record">-->
                  <!--{{$t('m.navheader.entrustRecord')}}-->
                <!--</router-link>-->
              <!--</el-dropdown-item>-->
              <!--<el-dropdown-item>-->
                <!--<router-link tag="li" to="/order/turnover-record">-->
                  <!--{{$t('m.navheader.turnoverRecord')}}-->
                <!--</router-link>-->
              <!--</el-dropdown-item>-->
              <!--<el-dropdown-item>-->
                <!--<router-link tag="li" to="/order/recharge-record">-->
                  <!--{{$t('m.navheader.rechargeRecord')}}-->
                <!--</router-link>-->
              <!--</el-dropdown-item>-->
              <!--<el-dropdown-item>-->
                <!--<router-link tag="li" to="/order/withdraw-record">-->
                  <!--{{$t('m.navheader.withdrawRecord')}}-->
                <!--</router-link>-->

              <!--</el-dropdown-item>    &ndash;&gt;-->
            <!--</el-dropdown-menu>-->
          <!--</el-dropdown>-->
        <!--</div>-->



        <!--<router-link tag="li" to="/usercenter">
          <span :class="this.activeTab == 5 ? 'tab-link active-tab' : 'tab-link'">{{$t('m.navheader.usercenter')}}</span>
        </router-link>-->


      </ul>
      <ul class="nav-container-right">
        <!--商户中心-->
        <router-link tag="li" to="/usercenter" v-if="username">
          <span :class="activeTab == 5 ? 'tab-link active-tab' : 'tab-link'">{{$t('m.navheader.usercenter')}}</span>
        </router-link>
        <!--个人中心-->
        <div class="menuitem" v-if="username">
          <el-dropdown :show-timeout="0" >
            <span class="el-dropdown-link" :class="this.activeTab == 3 ? 'active-tab' : ''">
              {{$t('m.navheader.assets')}}<i class="el-icon-arrow-down nav-icon-right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <router-link tag="li" to="/finance">
                  {{$t('m.navheader.accountAssets')}}
                </router-link>
              </el-dropdown-item>
              <el-dropdown-item>
                <router-link tag="li" to="/order/entrust-record">
                  {{$t('m.navheader.entrustRecord')}}
                </router-link>
              </el-dropdown-item>
              <el-dropdown-item>
                <router-link tag="li" to="/order/turnover-record">
                  {{$t('m.navheader.turnoverRecord')}}
                </router-link>
              </el-dropdown-item>

               <el-dropdown-item>
                <router-link tag="li" to="/order/recharge-record">
                  {{$t('m.navheader.rechargeRecord')}}
                </router-link>
              </el-dropdown-item>

              <el-dropdown-item>
                <router-link tag="li" to="/order/withdraw-record">
                  {{$t('m.navheader.withdrawRecord')}}
                </router-link>
              </el-dropdown-item>

              <!-- <el-dropdown-item>
               <router-link tag="li" to="/finance/btcx-to-cny">
               BTCX兑换
               </router-link>
               </el-dropdown-item>-->
              <el-dropdown-item>
                <router-link tag="li" to="/finance/address-manager">
                  {{$t('m.navheader.addressManagement')}}
                </router-link>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
        <template v-if="username">
          <router-link tag="li" to="/usercenter" class="username"><span>{{username}}</span></router-link>
          <li @click="logout"><span>{{$t('m.navheader.signout')}}</span></li>
        </template>
        <template v-else>
          <router-link tag="li" to="/login"><span>{{$t('m.navheader.signin')}}</span></router-link>
          <router-link tag="li" to="/regist"><span>{{$t('m.navheader.signup')}}</span></router-link>
        </template>


        <el-dropdown :show-timeout="0">
          <span class="el-dropdown-link">
            {{$t('m.navheader.language')}}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="changeLanguage('zh-CN')">简体中文</el-dropdown-item>
            <el-dropdown-item @click.native="changeLanguage('zh-HK')">繁體中文</el-dropdown-item>
            <el-dropdown-item @click.native="changeLanguage('en-US')">English</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </ul>
    </div>
  </div>
</template>

<script>
  import {commonApi} from "../../api/common";
  import {mapMutations, mapGetters} from 'vuex'

  export default {
    props: ["activeTab", "nobackground"],
    computed: {
      ...mapGetters(['mobile', 'username', 'token', 'lang'])
    },
    data() {
      return {
        refreshTokenTimer: 0, // 刷新TOKEN 定时器ID
        cache: localStorage
      }
    },
    created() {
      this.getUserInfo();
    },
    mounted() {
      this.$i18n.locale = this.lang;
      this.setLang(this.lang)
      if (this.nobackground) {
        this.$refs.myHeader.style.background = "none"
      }
    },
    methods: {
      changeLanguage(lang) {
        this.$i18n.locale = lang;
        this.setLang(lang)
        this.bus.$emit("change:language", lang);
      },
      clearUserInfo(){
        this.setExpireTime('')
        this.setToken('')
        this.setUserName('')
        this.setEmail("")
        window.location.reload()
      },
      getUserInfo() {
        if (this.token !== '' && this.token !== null) {
          this.getUserInfoByToken(this.token);
        }
      },
      // 通过token获取用户信息
      getUserInfoByToken(token) {
        commonApi.serverUserinfo(token).then(res => {
          let {data} = res;
          this.setUserName((data.username) ? data.username : (data.mobile?data.mobile:data.email));
          this.setMobile(data.mobile);
          this.setCountryCode(data.countryCode);
          this.setInviteCode(data.inviteCode);
          this.setGaStatus(data.gaStatus)
          this.setEmail(data.email==undefined?"":data.email)

          if(data.idCardType) {
            this.setIdCardType(data.idCardType);
          }
          if(data.authtime) {
            this.setAuthTime(data.authtime);
          }
          this.setIsAuth(data.authStatus);
          this.setSeniorAuthDesc(data.seniorAuthDesc)
          this.setSeniorAuthStatus(data.seniorAuthStatus)

          if(data.idCard) {
            this.setIdCard(data.idCard.replace(data.idCard.substr(6,6), "******"));
          }

          //是否实名认证
          if(data.realName){
            this.setRealName(data.realName);
          }
          if (this.$route.path !== "/usercenter/complete-userinfo" &&
            this.$route.path !== "/usercenter/modify-id" &&
            this.$route.path !== "/usercenter/modify-id-senior") {
            if(data.paypassSetting === 0){
              this.setPayPassword(false);
              this.completeUserInformation();
            }else{
              this.setPayPassword(true);
            }
          }
        }, () => {
          // this.clearUserInfo();
        });
      },
      completeUserInformation() {
        this.$confirm(this.$t('m.userCenter.plzSetUserInfo'), this.$t('m.prompt'), {
          confirmButtonText: this.$t('m.userCenter.goSetting'),
          cancelButtonText: this.$t('m.no'),
          type: 'warning'
        }).then(() => {
          this.$router.push("/usercenter/complete-userinfo")
        }, () => {

        });
      },
      logout() {
        // this.$message('退出登录成功');
        this.clearUserInfo();
        this.$router.push('/home');
      },

      ...mapMutations({
        setUserName: 'SET_USERNAME',
        setMobile : 'SET_MOBILE',
        setTrueName: 'SET_TRUENAME',
        setIsAuth: 'SET_ISAUTH',
        setPayPassword: 'SET_PAYPASSWORD',
        setInviteCode: 'SET_INVITECODE',
        setIdCard: 'SET_IDCARD',
        setIdCardType: 'SET_IDCARDTYPE',
        setRealName: 'SET_REALNAME',
        setAuthTime: 'SET_AUTHTIME',
        setToken: 'SET_TOKEN',
        setRefreshToken: 'SET_REFRESH_TOKEN',
        setCountryCode: 'SET_COUNTRYCODE',
        setSeniorAuthStatus:"SET_SENIOR_AUTH_STATUS",
        setLang: 'SET_LANG',
        setExpireTime:"SET_EXPIRE_TIME",
        setSeniorAuthDesc:"SET_SENIOR_AUTH_DESC",
        setGaStatus:"SET_GA_STATUS",
        setEmail:"SET_EMAIL"
      }),
    },

    destroyed() {
      if (this.refreshTokenTimer) {
        clearTimeout(this.refreshTokenTimer);
        this.refreshTokenTimer = 0;
      }
    }
  }
</script>

<style scoped lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/variable"

  .el-dropdown-menu .el-dropdown-menu__item li {
    white-space: nowrap !important
  }

  .m-header {
    width:100%
    padding: 0 20px;
    height 60px;
    line-height: 60px
    box-sizing border-box;
    background #18191C
    color $color-text-nav
    font-size $font-size-medium-x
    .nav-container {
      overflow: hidden;
      height:100%;
      position relative;
      margin:0 auto;
      .logo {
        position absolute
        left:1%
        /* width 105px
         height 30px
         position: absolute
         top 50%
         margin-top -15px*/
      }
      .tab-link {
        &:hover {
          color $color-header-tab
        }
      }
      .active-tab {
        color $color-header-tab
      }
      .el-dropdown {
        color $color-text-nav
        font-size $font-size-medium-x
        cursor pointer
      }
      .nav-icon-right{
        margin-left 5px
        font-size 14px
      }
      .nav-container-left {
        float: left

        img{
          vertical-align middle
          margin-right 15px

        }
        li {
          float: left;
          margin-right 40px
          span {
            cursor: pointer
          }
        }
        .menuitem{
          float: left;
          padding-right: 40px
          a{
            color #fff
            &:hover{
              color: #178fe3
            }
          }
        }
      }
      .nav-container-right {
        float: right
        font-size $font-size-medium
        .el-dropdown {
          color $color-text-nav
          font-size $font-size-medium
          cursor pointer
        }
        .username{
          max-width 100px
          overflow hidden
          text-overflow ellipsis
          white-space nowrap
        }
        li {
          float: left;
          margin-right 40px
          span {
            cursor: pointer
          }
        }
      }

    }

  }
  .menuitem{
    display: inline-block
    float left
  }
</style>
