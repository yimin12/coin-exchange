<template>
  <div class="ucenter-wrap" v-wechat-title="$t('m.userCenter.fundPassword')">
    <NavHeader activeTab="5"></NavHeader>

    <div class="ucenter-content">
        <div class="ucenter-row">
            <sidebar :activeId="4"></sidebar>
            <div class="ucenter-main">
                <div class="ucenter-header">
                    <h3 class="title">{{$t('m.userCenter.fundPassword')}}</h3>
                </div>
                <div class="ucenter-body">
                    <el-row>
                      <el-col :span="13">
                        <el-form :label-position="labelPosition" :model="forms"  :rules="rules" ref="forms" label-width="140px">
                          <el-form-item class="suffix-link" :label="$t('m.userCenter.oldFundPassword')" prop="password">
                            <el-input type="password" :placeholder="$t('m.userCenter.oldFundPassword')" v-model="forms.password"
                                      :maxlength="20"
                                      auto-complete="off">
                              <router-link
                                slot="append"
                                to="/forget-trade-password"
                                class="link">
                                {{$t('m.userCenter.forgetPassword')}}
                              </router-link>
                            </el-input>

                          </el-form-item>
                          <el-form-item :label="$t('m.userCenter.newFundPassword')" prop="newpassword">
                            <el-input type="password" :placeholder="$t('m.userCenter.newFundPassword')" v-model="forms.newpassword" auto-complete="off"
                                      :maxlength="20"
                            ></el-input>
                          </el-form-item>
                          <el-form-item :label="$t('m.userCenter.confirmFundPassword')" prop="repassword">
                            <el-input type="password" :placeholder="$t('m.userCenter.confirmFundPassword')" v-model="forms.repassword" auto-complete="off"
                                      :maxlength="20"
                            ></el-input>
                          </el-form-item>

                          <el-form-item :label="$t('m.userCenter.validationCode')" prop="validateCode">
                            <el-col :span="14">
                              <el-input :placeholder="$t('m.userCenter.plzValidationCode')"
                                        v-model="forms.validateCode"
                                        :maxlength="6">
                              </el-input>
                            </el-col>
                            <el-col :span="1">&nbsp;</el-col>
                            <el-col :span="9">

                              <validateCode :mobile="forms.mobile" type="CHANGE_PAY_PWD_VERIFY"></validateCode>
                            </el-col>
                          </el-form-item>

                          <el-form-item>
                            <el-button type="primary" @click="submitForm('forms')">{{$t('m.submit')}}</el-button>
                          </el-form-item>
                          <el-form-item style="margin-top: -10px">
                            <el-button @click="resetForm('forms')" class="el-button--reset">{{$t('m.reset')}}</el-button>
                          </el-form-item>

                        </el-form>
                      </el-col>
                    </el-row>
                </div>
            </div>
        </div>
    </div>
    <div class="ucenter-footer">
      <mFooter class="footer-bar"></mFooter>
    </div>
  </div>
</template>
<script>
  import ToggleButton from 'vue-js-toggle-button'
  import Vue from 'vue'
  import NavHeader from 'components/nav-header/nav-header'
  import sidebar from 'components/usercenter/sidebar'
  import mFooter from 'components/m-footer/m-footer'
  import { util } from 'common/js/util'
  import { updatePayPassword } from 'api/usercenter'
  import { mapGetters } from 'vuex'
  import {commonApi} from "../../api/common";
  import loginMixin from "../../common/mixin/loginMixin";
  Vue.use(ToggleButton)
  export default {
    mixins:[loginMixin],
    data(){
      let passwordReg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$/
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error(this.$t('m.userCenter.plzOldPassword')));
        }else if (!(passwordReg.test(value))) {
          callback(new Error(this.$t('m.userCenter.passwordFormatError')));
        } else {
          callback();
        }
      };
      var validateNewPass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error(this.$t('m.userCenter.plzNewPassword')));
        }else if (value !== this.forms.newpassword) {
          callback(new Error(this.$t('m.userCenter.passwordFormatError')));
        } else {
          callback();
        }

      };
      var validateRePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error(this.$t('m.userCenter.plzPasswordAgain')));
        }else if (!(passwordReg.test(value))) {
          callback(new Error(this.$t('m.userCenter.passwordFormatError')));
        } else if (value !== this.forms.newpassword) {
          callback(new Error(this.$t('m.userCenter.confirmPwError')));
        } else {
          callback();
        }
      };
      var checkVcode =(rule, value, callback)=>{
        if (!value || !/^\d{6}/.test(value)) {
          callback(new Error(this.$t('m.userCenter.plzValidationCode')));
        }else{
          callback();
        }
      };
      return {
        labelPosition: 'left',
        forms: {
          mobile:'',
          password:'',
          newpassword: '',
          repassword: '',
          validateCode: '',
        },
        rules: {
          password: [
            { validator: validatePass, trigger: 'blur' }
          ],
          newpassword: [
            { validator: validateNewPass, trigger: 'blur' }
          ],
          repassword: [
            { validator: validateRePass, trigger: 'blur' },
            { validator: validateNewPass, trigger: 'blur' }
          ],
          validateCode: [
            { validator: checkVcode, trigger: 'blur' }
          ]
        }
      };
    },
    created(){

    },
    mounted(){
      this.forms.mobile = this.mobile;
      console.log("mounted:",this.forms.mobile)
    },
    components: {
      NavHeader,
      mFooter,
      sidebar
    },
    computed:{
      ...mapGetters(['mobile','token'])
    },
    methods   : {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            updatePayPassword({
              oldpassword: this.forms.password,
              newpassword: this.forms.newpassword,
              validateCode: this.forms.validateCode
            },this.token).then((res)=>{
              this.$message({
                message: this.$t('m.userCenter.modifySuccess'),
                type:"success"
              })
              this.$router.push("/usercenter")
            }).catch((res)=>{
              this.$message({
                message:res.errmsg,
                type:"error"
              })
            });
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },

    }
  }
</script>
<style lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/usercenter"
</style>
<style scoped lang="stylus" rel="stylesheet/stylus">
  .el-button--reset{
    width 100%
    background-color #F0F0F0
    border-color #e2e2e2
    color #AAABB1
  }
  .el-button--reset:hover{
    background-color #e6e6e6
    border-color #e6e6e6
  }
</style>

