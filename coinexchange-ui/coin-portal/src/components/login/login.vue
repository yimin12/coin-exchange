<template>
  <div class="account-entry" v-wechat-title="$t('m.navheader.signin')">
    <div class="topnav">
      <NavHeader :nobackground="true"></NavHeader>
    </div>
    <div class="account-wrap">
      <div class="account-content login">
        <el-form ref="form" :model="ruleForm" :rules="rules">
          <h3 class="title">{{$t('m.navheader.signin')}}</h3>
          <div class="content-box">
            <div class="box-r">
              <el-form-item>
                <el-select
                  v-model="ruleForm.countryCode"
                  filterable
                  :placeholder="$t('m.pleaseChoose')"
                  style="width: 100%">
                  <el-option v-for="item in countryCodeMap.options"
                             :key="item.name"
                             :label="item.name"
                             :value="item.code">
                  </el-option>
                </el-select>
              </el-form-item>

              <el-form-item prop="mobile" :error="errors.mobile">
                <el-input type="text"
                          v-model="ruleForm.mobile"
                          @keyup.enter.native="submitForm"
                          auto-complete="user-phone"
                          :placeholder="$t('m.loginRegist.plzInputAccount')"></el-input>
              </el-form-item>

              <el-form-item prop="password" :error="errors.password">
                <el-input type="password"
                          v-model="ruleForm.password"
                          @keyup.enter.native="submitForm"
                          auto-complete="user-password"
                          :maxlength="20"
                          :placeholder="$t('m.userCenter.plzPassword')"></el-input>
              </el-form-item>

              <el-form-item id="captcha" class="captcha" ref="captcha">
                <p :class="['wait',showCaptcha?'show':'hide']">{{$t('m.userCenter.verificationLoading')}}</p>
              </el-form-item>

              <div class="account-help">
                <div class="help-box">
                  <div class="left-btn">
                    <router-link to="/regist" class="a">{{$t('m.userCenter.registerAccount')}}
                    </router-link>
                  </div>
                  <div class="right-btn">
                    <router-link to="/forget-password" class="a">{{$t('m.userCenter.forgetPassword')}}
                    </router-link>
                  </div>
                </div>
              </div>

              <el-button @click="submitForm" :loading="loading" class="button-primary">
                {{$t('m.navheader.signin')}}
              </el-button>
            </div>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapMutations} from 'vuex'
  import NavHeader from 'components/nav-header/nav-header'
  import mFooter from 'components/m-footer/m-footer'
  import {loginRegist} from 'api/loginRegist'
  import loginMixin from "../../common/mixin/loginMixin";

  let Timer = 0;
  export default {
    mixins: [loginMixin],
    data() {
      return {
        errors: {
          mobile: '',
          password: '',

        },
        ruleForm: {
          countryCode: '+86',
          mobile: '',
          password: '',

        },
        //表单验证规则
        rules: {
          mobile: [
            {validator: this.checkAccout, trigger: 'blur'}
          ],
          password: [
            {validator: this.checkPassword, trigger: 'blur'}
          ],

        }
      }
    },
    components: {
      NavHeader,
      mFooter
    },
    methods: {
      // captchaSuccessFun() {
      //   this.submitForm();
      // },
      processGA() {
        // 判断是否开启google 验证
        this.setGaStatus(1)
        this.showGA()
      },
      // 显示谷歌验证
      showGA() {
        let that = this;
        this.$prompt(this.$t('m.userCenter.plzInputGA'),this.$t('m.userCenter.GAOpened'), {
          confirmButtonText: this.$t('m.yes'),
          cancelButtonText: this.$t('m.no'),
          inputPattern: /^\d{6}$/,
          inputErrorMessage: this.$t('m.userCenter.plzInputGA'),
        }).then(({value}) => {
          that.captchaObj.reset();
          that.loading = false

          that.submitForm(true,value)

        }).catch(() => {
          console.log("catch->")
          that.captchaObj.reset();
          that.loading = false
        });
      },


      processLogin(data) {
        let expireTime = JSON.stringify({
          updateTime: +new Date(),
          expire: data.expire,
        });
        this.setUserName(data.username)
        this.setExpireTime(expireTime)
        this.setToken(data.access_token)
        this.setRefreshToken(data.refresh_token)
        let willJump = this.$route.query.redirect;
        if (willJump !== undefined) {
          this.$router.replace(willJump); //预跳转页
        } else {
          this.$router.push('/'); //跳转首页
        }
      },
      // 登录
      submitForm(checkStatus,ga_code) {
        let root = this;
        this.$refs.form.validate((valid) => {
          if (!valid) {
            root.captchaObj.reset();
            return false;
          }
          // 极验返回结果有延迟，这里也要延迟
          if (Timer) clearTimeout(Timer);
          const {geetest_challenge, geetest_validate, geetest_seccode} = root.captchaSuccess;
          if (!geetest_challenge || !geetest_validate || !geetest_seccode) {
            if (checkStatus === true) {
              root.$message({
                message: this.$t('m.userCenter.geeTestButtonText'),
                type: 'error'
              });
              return;
            } else {
              Timer = setTimeout(() => {
                this.submitForm(true)
              }, 1000);
              return;
            }
          } 
          this.loading = true;


          if (this.errors.mobile !== '') {
            this.errors.mobile = '';
          }
          if (this.errors.password !== '') {
            this.errors.password = '';
          }

          let data = {
            countryCode: this.ruleForm.countryCode,
            username: this.ruleForm.mobile,
            password: this.ruleForm.password ,
            uuid: root.uuidCode,
            geetest_challenge,
            geetest_validate,
            geetest_seccode,
            ga_code 
          };
          loginRegist.login(data).then(res => {
            console.log("结果",res)
            let data = res.data;
            this.processLogin(data)


          }).catch((err) => {
            if(err.errcode === 40014){
              this.processGA();
            }else{
              this.captchaObj.reset();
            }
            this.loading = false;
          })
        });
      },

      ...mapMutations({
        setUserName: 'SET_USERNAME',
        setExpireTime: 'SET_EXPIRE_TIME',
        setToken: "SET_TOKEN",
        setRefreshToken: "SET_REFRESH_TOKEN" ,
        setGaStatus: "SET_GA_STATUS"
      }),
    },
  }

</script>

<style lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/login"
  .wait {
    color: #fff;
  }

  .show {
    display: block;
  }

  .hide {
    display: none;
  }

  .captcha {
    padding-bottom 12px;
  }
</style>
