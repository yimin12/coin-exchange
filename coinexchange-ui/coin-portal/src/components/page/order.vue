<template>
  <div class="ucenter-wrap about-page" v-wechat-title="$t('m.footer.order')">
    <NavHeader></NavHeader>
    <div class="ucenter-content">
      <div class="ucenter-row">
        <sidebar></sidebar>
        <div class="ucenter-main">
          <div class="ucenter-header">
              <h3 class="title">{{$t('m.footer.order')}}</h3>
          </div>
          <div class="ucenter-body">
            <div v-if="token">
              <el-form ref="forms" size="small" :model="forms" :rules="rules" label-position="top" label-width="80px">
                <el-form-item :label="$t('m.SubmitTheRepairOrder')" prop="question">
                  <el-input type="textarea" v-model="forms.question"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" style="width:auto" @click="onSubmit">{{$t('m.accountAssets.submit')}}</el-button>
                </el-form-item>
              </el-form>
              <div class="records">
                <ul>
                  <li v-for="item in data">
                    <span class="time">{{item.created}}</span>
                    <strong class="username">{{username}}</strong>
                    <p class="question">{{item.question}}</p>
                    <p v-if="item.status == 2" class="reply">
                      <span class="username reply-username">{{item.answerName}}:</span>{{item.answer}}
                    </p>
                  </li>
                </ul>
              </div>
              <el-pagination
                layout="prev, pager, next"
                @current-change="handleCurrentChange"
                :page-size="size"
                :current-page.sync="current"
                :total="total">
              </el-pagination>
            </div>
            <div v-else>
              <p class="need-login">
                {{$t('m.notLogin')}}
                <router-link to="/login" class="a">{{$t('m.navheader.signin')}}</router-link>
                {{$t('m.or')}}
                <router-link to="/regist" class="a">{{$t('m.navheader.signup')}}</router-link>
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="ucenter-footer">
      <mFooter class="footer-bar"></mFooter>
    </div>
  </div>
</template>
<style lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/usercenter"
  .about-page{
    .ucenter-body{
      color #666666
      line-height 1.77
      padding-bottom 50px !important
      h3{
        font-weight bold
        margin-top 15px
        margin-bottom 20px
      }
      .need-login{
        padding-top 100px
        text-align center
      }
      .a{
        color #7392ff
      }
      .records{
        margin-top 10px
        ul{
          li{
            border-top 1px solid #eee
            padding 15px 0
            font-size 14px
            border-radius 5px
            .time{
              float right
              color #888
            }
            .username{
              font-weight bold
              margin-right 5px
            }
            .question{
              color #000
              white-space normal
              word-break break-word
            }
            .reply{
              color #7392ff
              margin-top 10px
              padding 5px 8px
              background-color #f7f9ff
              border-radius 5px
              white-space normal
              word-break break-word
            }
          }
        }
      }
    }

  }
</style>
<script>
  import NavHeader from 'components/nav-header/nav-header'
  import sidebar from 'components/page/sidebar'
  import mFooter from 'components/m-footer/m-footer'
  import {commonApi} from "../../api/common";
  import {mapGetters} from 'vuex'

  export default {
    data(){
      let checkContent = (rule, value, callback) => {
        value = value.trim();
        if(value === '') {
          callback(new Error(this.$t('m.emptyContent')))
        }else{
          callback()
        }
      };
      return {
        forms : {
          question : ""
        },
        rules : {
          question:[
            {validator: checkContent, trigger: 'blur'}
          ]
        },
        size: 5,
        current: 1,
        total: 0,
        data: [],
        lang: "zh-CN",
      }
    },
    computed:{
      ...mapGetters(['token','username'])
    },
    components : {NavHeader,sidebar,mFooter},
    created() {
      this.bus.$on("change:language", (lang)=>{
        this.lang = lang.toLowerCase();
      })
    },
    methods : {
      onSubmit(){
        this.$refs.forms.validate((valid)=>{
          if(valid) {
            commonApi.addWorkIssue(this.forms.question, this.token).then(res=>{
              this.$message({
                type   : "success",
                message: this.$t('m.addIssueSuccess')
              });
              this.forms.question = '';
              this.getIssueAnswer();
            })
          }
        });
      },

      async getIssueAnswer() {
        let res = await commonApi.getWorkIssueList(this.current,this.size,this.token);
        this.total = res.data.total;
        this.data = res.data.records;

        console.log(res)
      },

      handleCurrentChange() {
        this.getIssueAnswer();
      }
    },
    mounted() {
      if(this.token) {
        this.getIssueAnswer();
      }
    }
  }
</script>
