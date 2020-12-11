<template>
  <div class="ucenter-wrap">
    <NavHeader activeTab="5"></NavHeader>

    <div class="ucenter-content">
      <div class="ucenter-row">
        <sidebar :activeId="5"></sidebar>
        <div class="ucenter-main">
          <div class="ucenter-header">
            <h3 class="title">{{$t('m.userCenter.googleVerification')}}</h3>
          </div>
          <div class="ucenter-body">
            <el-row :gutter="20">
              <el-col :span="10">
                <div ref="qrcodeImg"></div>
              </el-col>
              <el-col :span="12" >
                <el-alert
                  :title="$t('m.prompt')"
                  type="success"
                  class="operate-alert"
                  :closable="false">
                  <div class="google-tips">
                    <ul>
                      <li v-for="index in 3">{{$t(`m.userCenter.googleTips.${index}`)}}</li>
                    </ul>
                    <p>{{$t(`m.userCenter.googleTips[3]`)}}</p>
                    <p>
                      <strong >{{ruleForm.secret}}</strong>
                      <el-button type="success"
                                 size="mini"
                                 v-clipboard:copy="ruleForm.secret"
                                 v-clipboard:success="onCopy"
                                 v-clipboard:error="onError"
                                 round>{{$t('m.userCenter.copy')}}
                      </el-button>
                    </p>
                  </div>
                </el-alert>
                <el-form :label-position="labelPosition" :model="ruleForm" :rules="rules" ref="form"
                         label-width="100px">
                  <el-form-item :label="$t('m.userCenter.GA')" prop="code">
                    <el-input type="text" v-model="ruleForm.code" auto-complete="off" :maxlength="6"></el-input>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" @click="submitForm('form')">验证并开启</el-button>
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
  import {util} from 'common/js/util'
  import {getchilds, getLoginRecord, gaGenerate, gaVerify} from 'api/usercenter'
  import VueClipboard from 'vue-clipboard2'
  import QRCode from 'qrcodejs2'
  import {mapGetters } from 'vuex'

  Vue.use(ToggleButton)
  Vue.use(VueClipboard)
  export default {
    computed:{
      ...mapGetters(['token'])
    },
    data() {
      let gAreg = /^\d{6}$/;
      let checkGA = (rule, value, callback) => {
        if (value === '') {
          callback(new Error(this.$t("m.userCenter.plzInputGA")));
        } else if (!gAreg.test(value)) {
          callback(new Error(this.$t('m.userCenter.errorGA')));
        } else {
          callback();
        }
      };
      return {
        labelPosition: 'top',
        ruleForm: {
          code: '',
          secret:''
        },
        rules: {
          code: [
            {validator:checkGA,trigger:'blur'}
          ]
        }
      };
    },
    created() {

    },
    mounted() {
      this._gaGenerate()
    },

    components: {
      NavHeader,
      mFooter,
      sidebar
    },
    methods: {
      async _gaGenerate() {
        let res = await gaGenerate(this.token)
        this.url = res.data.otpAuthTotpURL
        this.ruleForm.secret = res.data.secret_key

        let ref = this.$refs.qrcodeImg
        let qrcode = new QRCode(ref, {
          text: this.url,
          width: 250,
          height: 250,
          colorDark: "#000000",
          colorLight: "#ffffff",
          correctLevel: QRCode.CorrectLevel.H
        });

        console.log("google:", res)
      },
      async _gaVerify(){
        let res = await gaVerify(this.ruleForm.code,this.ruleForm.secret);
        if(res.errcode === 0 ){
          this.$message({
            message:this.$t("m.userCenter.openGaSuccess"),
            type:"success"
          })
          this.$router.push("/usercenter/modify-google-auth")
        }
        console.log("开启google",res)
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this._gaVerify()
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      onCopy(e) {
        this.$message({
          message: this.$t('m.copySuccess'),
          type: 'success'
        });
      },
      onError() {
        this.$message(this.$t('m.copyError'));
      }
    }
  }
</script>
<style lang="stylus" scoped>
  .operate-alert {
    margin-bottom 20px
  }

  .google-tips {
    font-size 14px
    line-height 1.7
    margin-bottom 15px
    ul {
      margin-left 20px
      margin-top 15px
      margin-bottom 15px
      li {
        list-style-type square
      }
    }
    p {
      strong {
        font-weight bold
        margin-right 10px
      }
    }
  }
</style>
<style lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/usercenter"
</style>
