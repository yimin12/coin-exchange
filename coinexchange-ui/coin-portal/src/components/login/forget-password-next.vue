<template>
  <div class="account-entry">
    <div class="topnav">
      <NavHeader ></NavHeader>
    </div>
    <div class="account-wrap">
      <div class="account-content forget-password">
          <el-form ref="AccountFrom" :model="account" :rules="rules" label-position="left" label-width="0px"
                   class="demo-ruleForm login-container">
            <h3 class="title">{{$t('m.userCenter.resetLoginPassword')}}</h3>
            <el-form-item prop="u_password" class="space">
              <el-input type="password" v-model="account.u_password" auto-complete="off"
                        :placeholder="$t('m.userCenter.setLoginPassword')"></el-input>
            </el-form-item>
            <el-form-item prop="u_repassword" class="space">
              <el-input type="password" v-model="account.u_repassword" auto-complete="off"
                        :placeholder="$t('m.userCenter.confirmLoginPassword')"></el-input>
            </el-form-item>
            <el-form-item style="width:100%; margin-bottom: 5px">
              <el-button type="primary" class="button-primary" @click="handleResetPwd" :loading="loading">{{$t('m.userCenter.complete')}}
              </el-button>
            </el-form-item>
          </el-form>
      </div>
    </div>
  </div>
</template>

<script>
  import md5 from 'js-md5'
  import NavHeader from 'components/nav-header/nav-header'
  import mFooter from 'components/m-footer/m-footer'
  import { loginRegist } from 'api/loginRegist'
  import { OK } from 'api/config'
  import { cookieUtil } from 'common/js/cookieUtil'
  import  validateCode  from 'base/validate-code/validate-code'
  import { commonApi } from 'api/common'

  export default {

    data() {


      let passwordReg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$/

      let validatePassComplex = (rule, value, callback) => {
        let lang = this.lang;
        value = value.trim();
        if(value === ''){
          let emptyError = {
            'zh-CN' : "请设置您的账号密码",
            'zh-HK' : "請設置您的賬號密碼",
            'en-US' : "Please set your account password"
          }
          callback(new Error(emptyError[lang]));
        }else if (!(passwordReg.test(value))) {
          let typeError = {
            'zh-CN' : "密码长度为8-20位，必须包括字母、数字",
            'zh-HK' : "密碼長度為8-20位，必須包括字母、數字",
            'en-US' : "Password must contain letters, numbers, and lengths of 8 to 20"
          }
          callback(new Error(typeError[lang]));
        } else {
          callback();
        }
      };
      let validatePass = (rule, value, callback) => {
        let lang = this.lang;
        if (value === '') {
          let emptyError = {
            'zh-CN' : "请再次输入密码",
            'zh-HK' : "請再次輸入密碼",
            'en-US' : "Please enter your password again"
          }
          callback(new Error(emptyError[lang]));
        } else if (value !== this.account.u_password) {
          let __error = {
            'zh-CN' : "两次输入密码不一致!",
            'zh-HK' : "兩次輸入密碼不壹致!",
            'en-US' : "Two input passwords are not the same"
          }
          callback(new Error(__error[lang]));
        } else {
          callback();
        }
      };

      return {
        validateImg: '',
        loading   : false,
        id:'',
        account    : {
          id:'',
          u_password  : '',
          u_repassword: '',
        },
        //表单验证规则
        rules      : {
          u_password  : [
            {validator: validatePassComplex, trigger: 'blur'}
          ],
          u_repassword: [
            {validator: validatePass, trigger: 'blur'}
          ],
        },
        lang : "zh-CN"
      }
    },
    mounted() {
      this.id = this.$route.params.id

    },
    components: {
      NavHeader,
      mFooter,
      validateCode
    },
    created(){
      this.bus.$on("change:language", (lang)=>{
        this.lang = lang.toLowerCase();
      })
    },
    methods   : {
      handleResetPwd() {
        let lang = this.lang;
        this.$refs.AccountFrom.validate((valid) => {
          if (valid) {
            this.loading = true;
            var params    = {
              id:this.id,
              newpassword  : md5(this.account.u_password),
              repassword: md5(this.account.u_repassword),
            };
            commonApi.registUserPassword(params).then(res => {
              this.loading = false;

              if (res.statusCode === OK) {
                this.$router.push("/usercenter")
                let msg = {
                  'zh-CN' : "重置登录密码成功!",
                  'zh-HK' : "重置登錄密碼成功!",
                  'en-US' : "Login password reset successful"
                }
                this.$message({
                  message: msg[lang],
                  type   : 'success'
                });

              } else {
                this.$message({
                  message: res.errmsg,
                  type   : 'error'
                });
              }
            })
          } else {
            console.log('error submit');
            return false;
          }
        });
      }
    }
  }
</script>
<style lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/login"
</style>
