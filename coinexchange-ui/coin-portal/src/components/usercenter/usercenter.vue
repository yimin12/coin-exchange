<template>
  <div class="ucenter-wrap"  v-wechat-title="$t('m.navheader.usercenter')">
    <NavHeader activeTab="5"></NavHeader>

    <div class="ucenter-content">
      <div class="ucenter-row">
        <sidebar :activeId="0"></sidebar>
        <div class="ucenter-main">
          <div class="ucenter-header">
            <h3 class="title">{{$t('m.userCenter.accountInfo')}}</h3>
            <div class="right-bar">
              <!--                       <div class="tips">
                                      <span class="tips-text">使用HHT支付交易手续费（50%折扣）</span>
                                      <toggle-button :value="true" color="#5BBC51" :labels="{checked: 'on', unchecked: 'off'}"></toggle-button>
                                    </div> -->
            </div>
          </div>
          <div class="ucenter-body">
            <el-row>
              <el-col :span="14">
                <el-form ref="form" label-position="left" label-width="140px">
                  <el-form-item :label="$t('m.userCenter.username')" prop="username">
                    <el-input type="text" v-model="username" auto-complete="off"
                              :disabled="true"></el-input>
                  </el-form-item>

                  <el-form-item :label="$t('m.userCenter.mobile')" prop="mobile">
                    <el-input type="text" v-model="formatMobile" auto-complete="off"
                              :disabled="true"></el-input>
                  </el-form-item>

                  <el-form-item class="copy-btn" :label="$t('m.userCenter.inviteCode')"
                                prop="inviteCode">
                    <el-col>
                      <el-input type="text" v-model="inviteCode" auto-complete="off"
                                :disabled="true">
                        <el-button slot="append" v-clipboard:copy="inviteCode"
                                   v-clipboard:success="onCopy"
                                   v-clipboard:error="onError"
                                   :plain="true"
                                   size="mini"
                        >{{$t('m.userCenter.copy')}}
                        </el-button>
                      </el-input>
                    </el-col>

                  </el-form-item>

                  <el-form-item class="copy-btn" :label="$t('m.userCenter.inviteLink')"
                                prop="inviteLink">
                    <el-input type="text" v-model="inviteLink" auto-complete="off" :disabled="true">
                      <el-button slot="append" v-clipboard:copy="inviteLink"
                                 v-clipboard:success="onCopy"
                                 v-clipboard:error="onError"
                                 :plain="true"
                                 size="mini"
                      >{{$t('m.userCenter.copy')}}
                      </el-button>
                    </el-input>
                  </el-form-item>

                  <el-form-item :label="$t('m.userCenter.qrcode')" prop="inviteLink">
                    <div ref="qrcodeImg"></div>
                    <a href="javascript:;" class="link suffix"
                       style="position:absolute;top:25px;left:120px"
                       @click="saveQC">{{$t('m.userCenter.saveQRCode')}}</a>
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
  import {cookieUtil} from 'common/js/cookieUtil'

  import {getchilds, getLoginRecord, getAuth} from 'api/usercenter'
  import {OK} from 'api/config'
  import QRCode from 'qrcodejs2'
  import {mapMutations, mapGetters} from 'vuex'
  import VueClipboard from 'vue-clipboard2'

  Vue.use(VueClipboard)
  Vue.use(ToggleButton)

  export default {
    data() {
      return {
        inviteCodeAddr: `${process.env.DOMAIN}/#/regist/`,    //邀请码链接
        hhtDiscount: true,
        childTbDataPage: [],
        childTbData: [],
        loginRecordTbData: [],
//        lastLogin:'',
        idCardTypeMap: {
          1: "身份证",
          2: "军官证",
          3: "护照",
          4: "台湾居民通行证",
          5: "港澳居民通行证",
          9: "其他"
        },
        isFirstAuth: false,    //是否初级认证
      }
    },
    components: {
      NavHeader,
      sidebar,
      mFooter
    },
    created() {

      this.bus.$on("change:language", (lang) => {
        let idCardTypeMap = {
          'zh-CN': {
            1: "身份证",
            2: "军官证",
            3: "护照",
            4: "台湾居民通行证",
            5: "港澳居民通行证",
            9: "其他"
          },
          'zh-HK': {
            1: "身份證",
            2: "軍官證",
            3: "護照",
            4: "臺灣居民通行證",
            5: "港澳居民通行證",
            9: "其他"
          },
          'en-US': {
            1: "ID Card",
            2: "Certificate of officers",
            3: "Passport",
            4: "Taiwan resident pass",
            5: "Passport of Hong Kong and Macao residents",
            9: "Other"
          }
        };

        this.idCardTypeMap = idCardTypeMap[lang.toLowerCase()]
      })

    },
    mounted() {
      let ref = this.$refs.qrcodeImg
      let qrcode = new QRCode(ref, {
        text: this.inviteLink,
        width: 90,
        height: 90,
        colorDark: "#000000",
        colorLight: "#ffffff",
        correctLevel: QRCode.CorrectLevel.H
      });
    },
    computed: {
      ...mapGetters([
        'isAuth',
        'mobile',
        'username',
        'trueName',
        'inviteCode',
        'payPassSetting',
        'idCard',
        'token',
      ]),
      inviteLink() {
        return this.inviteCodeAddr + this.inviteCode
      },
      formatMobile() {
        // return util.formatPhone(this.username);
        return this.username;
      },
    },
    methods: {

      onCopy: function (e) {
        this.$message({
          message: this.$t('m.copySuccess'),
          type: 'success'
        });
      },
      onError: function (e) {
        this.$message(this.$t('m.copyError'));
      },
      setCookie: function (cname, cvalue, exdays) {
        var d = new Date();
        d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
        var expires = "expires=" + d.toUTCString();
        console.info(cname + "=" + cvalue + "; " + expires);
        document.cookie = cname + "=" + cvalue + "; " + expires;
        console.info(document.cookie);
      },
      trim: function (str) {
        var val = str + '';
        return val.replace(/(^\s*)|(\s*$)/g, "");
      },
      handlePageChange(page) {
        let start = this.pageSize * (page - 1);
        let end = start + this.pageSize;
        this.childTbDataPage = this.childTbData.slice(start, end);
      },
      /*_getLoginRecord(){
        getLoginRecord().then(res => {
          if (res.statusCode === OK) {
            let result             = res.result
            this.loginRecordTbData = result
          }
        })
      },*/
      ...mapMutations({
        setIsAuth: 'SET_ISAUTH',
      }),
      // handleCopy(value) {
      //   document.addEventListener('copy', (e) => {
      //     e.clipboardData.setData('text/plain', value)
      //     e.preventDefault()
      //   })
      //   document.execCommand("copy")
      //   this.$message({
      //     message: '复制成功！',
      //     type: 'success'
      //   });
      // },
      saveQC() {
        let qc = this.$refs.qrcodeImg
        let img = qc.getElementsByTagName('img')[0]
        let a = document.createElement("a");
        a.href = img.src;
        a.download = "qrcode.png";
        a.click();
      }
    }
  }
</script>
<style lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/usercenter"
</style>
