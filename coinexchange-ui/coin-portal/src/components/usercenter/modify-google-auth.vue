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
              <el-col :span="12">
                <el-alert
                  :closable="false"
                  show-icon
                  :title="$t('m.userCenter.GAOpened')"
                  type="warning">
                </el-alert>
                <el-form :label-position="labelPosition" :model="ruleForm" :rules="rules" ref="form"
                         label-width="100px">
                  <el-form-item :label="$t('m.userCenter.GA')" prop="code">
                    <el-input type="text" v-model="ruleForm.code" auto-complete="off" :maxlength="6"></el-input>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="primary" @click="submitForm('form')">关闭google验证</el-button>
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
  import NavHeader from 'components/nav-header/nav-header'
  import sidebar from 'components/usercenter/sidebar'
  import mFooter from 'components/m-footer/m-footer'
  import {getchilds, getLoginRecord, gaGenerate, gaVerify,closeGaVerify} from 'api/usercenter'
  import {mapGetters } from 'vuex'

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
    components: {
      NavHeader,
      mFooter,
      sidebar
    },
    methods: {
      async _closeGaVerify(){
        let res = await closeGaVerify(this.ruleForm.code);
        if(res.errcode === 0 ){
          this.$message({
            message:this.$t("m.userCenter.closeGaSuccess"),
            type:"success"
          })
          this.$router.push("/usercenter/set-google-auth")
        }
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this._closeGaVerify()
          } else {
            console.log('error submit!!');
            return false;
          }
        });
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
