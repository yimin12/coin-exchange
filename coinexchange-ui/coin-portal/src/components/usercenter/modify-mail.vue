<template>
  <div class="main">
    <NavHeader activeTab="5"></NavHeader>


    <div class="content">
      <financeHeader first="个人中心" second="修改邮箱" jumpto="/usercenter" ></financeHeader>
      <div class="titleitem">修改绑定邮箱</div>
      <div class="contentitem">

        <div class="win500">
          <el-form :label-position="labelPosition" :model="ruleForm"  :rules="rules" ref="ruleForm" label-width="140px" class="demo-ruleForm">
            <el-form-item label="原邮箱" prop="oldmail">
              <el-input type="text" v-model="ruleForm.oldmail" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="新邮箱" prop="newmail">
              <el-input type="text" v-model="ruleForm.newmail" auto-complete="off"></el-input>
            </el-form-item>

            <el-form-item label="短信验证码" prop="vcode">
              <el-input placeholder="请输入验证码" v-model="ruleForm.vcode" class="input-with-select">
                <el-button slot="append" v-on:click="getVCode" v-html="ruleForm.sendVCodeText" style="width: 100px; text-align: center; padding: 0"></el-button>
              </el-input>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
              <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
          </el-form>
        </div>

      </div>
    </div>
    <mFooter></mFooter>

  </div>
</template>
<script>
  import ToggleButton from 'vue-js-toggle-button'
  import Vue from 'vue'
  import NavHeader from 'components/nav-header/nav-header'
  import mFooter from 'components/m-footer/m-footer'
  import { util } from 'common/js/util'
  import { getchilds, getLoginRecord } from 'api/usercenter'
  import { OK } from 'api/config'
  import financeHeader from 'components/finance/finance-header'

  Vue.use(ToggleButton)
  export default {
    data(){
      let reg = /^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+(\.[a-zA-Z]{2,3})+$/;
      var validateOldmail = (rule, value, callback) => {
        if (!value) return callback(new Error('请输入旧邮箱'));
        if(!reg.test(value)){
          return callback(new Error('邮箱格式不正确！'));
        }else{
          callback();
        };
      };
      var validateNewmail = (rule, value, callback) => {
        if (!value) return callback(new Error('请输入新邮箱'));
        if(!reg.test(value)){
          return callback(new Error('邮箱格式不正确！'));
        }else{
          callback();
        };
      };
      var checkVcode = (rule, value, callback) => {
        if (!value) return callback(new Error('验证码不能为空'));
        callback();
      };
      return {
        labelPosition: 'top',
        ruleForm: {
          oldmail: '',
          newmail: '',
          vcode: '',
          sendVCodeText: '发送验证码',
          sendVCodeOn: false
        },
        rules: {
          oldmail: [
            { validator: validateOldmail, trigger: 'blur' }
          ],
          newmail: [
            { validator: validateNewmail, trigger: 'blur' }
          ],
          vcode: [
            { validator: checkVcode, trigger: 'blur' }
          ]
        }
      };
    },
    created(){

    },
    mounted(){

    },
    components: {
      NavHeader,
      mFooter,
      financeHeader
    },
    methods   : {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            alert('submit!');
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      getVCode() {
        if(this.ruleForm.sendVCodeOn) return;
        this.ruleForm.sendVCodeOn = true;
        this.ruleForm.sendVCodeText = 60;
        let t = setInterval(() => {
          this.ruleForm.sendVCodeText--;
          if(this.ruleForm.sendVCodeText<1){
            clearInterval(t);
            this.ruleForm.sendVCodeOn = false;
            this.ruleForm.sendVCodeText = '重新发送';
          }
        },1000)
      }
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/usercenter"
</style>
<style scoped lang="stylus" rel="stylesheet/stylus">
  .main {
    background #fff
    .content {
      width 1200px
      margin 0 auto
      .profile {
        display flex
        margin-top 19px
        height 172px
        border: 1px solid $color-global-border
        align-items: center;
        .usercenter-text-big {
          font-size $font-size-large
        }
        .usercenter-text-small {
          font-size $font-size-medium
        }
        .profile-item {
          flex 1
          display: flex
          align-items center
          justify-content space-around
          img {
            margin-left 50px
          }
          .profile-item-centent {
            flex 1
            margin-left 27px
            height 101px
            display flex
            flex-direction column
            justify-content space-around

            .loginDate {
              font-size $font-size-medium
              color $color-usercenter-gray-text
            }
          }
        }

        .vertical-divider {
          width 1px;
          height 100px;
          background $color-global-border
        }
      }

      .secure {
        display flex
        justify-content space-between
        .secure-column {
          display flex
          flex 0 0 49.5%
          flex-direction column
          justify-content space-around

          .secure-column-item {
            border: 1px solid $color-global-border
            margin-top 10px
            .secure-item-title {
              background $color-global-text-dark-bg
              color $color-global-text-dark
              font-size $font-size-medium
              height 36px
              line-height 36px
              padding-left 17px
            }
            .secure-item-content {
              display flex
              height 110px
              justify-content space-around
              align-items center
              padding-left 16px
              color $color-global-text-dark
              .secure-item-content-center {
                margin-left 20px
                flex 1
                .content-subtitle {
                  color $color-global-text-light
                  margin-top 10px
                }
              }
              .item-btn {
                width 100px
                height 35px
                margin-right 10px
              }
            }
          }
        }
      }
      .invite-login {
        margin-top 37px
        .invite-login-title {
          font-size $font-size-large
          color $color-global-text-dark
          margin-bottom 10px
        }
        .tabheader {
          background $color-global-text-dark-bg
        }

        .el-pagination {
          margin 20px 0
          text-align center
        }
      }

    }

  }

</style>
