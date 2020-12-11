import {commonApi} from "../../api/common";
import captchaApi from "../../api/geetest";
import {countryCodeMap} from "../js/countryCodeMap";
import {mapGetters} from 'vuex'
import validateCode from 'base/validate-code/validate-code'



export default {
  components: {
    validateCode
  },
  computed: {
    ...mapGetters(['lang'])
  },
  mounted() {
    // console.log("mixin mounted", this.lang)
  },
  data() {

    return {
      countryCodeMap: countryCodeMap,
      loading: false,
      geetestLang: {
        "zh-CN": "zh",
        "zh-HK": "zh-tw",
        "en-US": "en"
      },
      showCaptcha: true,
      captchaObj: {},
      captchaSuccess: {},
      uuidCode: '',
    }
  },
  created() {
    // 初始化极验验证
    this.uuidCode = commonApi.uuid() ;
    this.initGtCaptcha();
    this.bus.$on("change:language", (lang) => {
      this.initGtCaptcha()
    })
  },
  methods: {
    checkValidateCode(rule, value, callback) {
      if(value == "") {
        callback(new Error(this.$t('m.loginRegist.plzInputCode')));
      }else if (!value || !/^\d{6}/.test(value)) {
        callback(new Error(this.$t('m.loginRegist.errorCode')));
      } else {
        callback();
      }
    },
    checkAccout(rule, value, callback) {
      value = value.trim();
      if (value === '') {
        callback(new Error(this.$t('m.loginRegist.plzInputAccount')));
      } else {
        callback();
      }
  },
  checkMobile(rule, value, callback) {
    value = value.trim();
    let countryCode = this.ruleForm.countryCode;
    this.errors.mobile = '';
    if (value === '') {
      callback(new Error(this.$t('m.userCenter.plzPhoneNumber')));
    } else if (!/^\d+/.test(value)) {
      callback(new Error(this.$t('m.userCenter.phoneFormatError')));
    } else {
      let reg = /^1(3|4|5|6|7|8|9)\d{9}$/;
      if (countryCode === '+86') {
        if (!reg.test(value)) {
          callback(new Error(this.$t('m.userCenter.phoneFormatError')));
        } else {
          callback();
          // if (typeof this._checkRegist === "function") {
          //   this._checkRegist(value, countryCode,0)
          // }
        }
      } else {

        callback();
        // if (typeof this._checkRegist === "function") {
        //   this._checkRegist(value, countryCode,0)
        // }
      }

    }
  },
  checkEmail(rule, value, callback) {
    let mailReg = /^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+(\.[a-zA-Z]{2,3})+$/;
    value = value.trim();
    if (value === '') {
      callback(new Error(this.$t('m.loginRegist.plzInputEmail')));
    } else if (!(mailReg.test(value))) {
      callback(new Error(this.$t('m.loginRegist.errorEmail')));
    } else {
      callback();
      // if (typeof this._checkRegist === "function") {
      //   this._checkRegist(value,"",1)
      // }
    }
  },

  checkPassword(rule, value, callback) {
    value = value.trim();
    if (value === '') {
      callback(new Error(this.$t('m.userCenter.plzPassword')));
    } else {
      callback()
    }
  },

    // 裁剪手机号
    slicePhoneNumber(num) {
      if (this.ruleForm.countryCode === '+86') {
        if (num.length > 11) {
          this.ruleForm.mobile = num.slice(0, 11);
        }
      }
    },
    // 极验
    initGtCaptcha() {
      const that = this;
      const init = (dat) => {
        const data = JSON.parse(dat) 
        window.initGeetest({
          // 以下 4 个配置参数为必须，不能缺少
          gt: data.gt,
          challenge: data.challenge,
          new_captcha: data.new_captcha, // 用于宕机时表示是新验证码的宕机
          offline: !data.success, // 表示用户后台检测极验服务器是否宕机，一般不需要关注
          product: "float", // 产品形式，包括：float，popup
          width: "100%"
        }, function (captchaObj) {
          that.captchaObj = captchaObj;
          let parentNode;
          let childs
          if (that.$refs.captcha) {
            parentNode = that.$refs.captcha.$el
            childs = parentNode.childNodes;
            for (let i = childs.length - 1; i >= 0; i--) {
              parentNode.removeChild(childs[i]);
            }
            captchaObj.appendTo('#captcha');
            captchaObj.onReady(function () {
              that.showCaptcha = false;
            });
            captchaObj.onSuccess(function () {
              that.captchaSuccess = captchaObj.getValidate();
              if (typeof that.captchaSuccessFun === 'function') {
                // that.captchaSuccessFun();
              }
            })
            captchaObj.onError(function () {
            });
          }
        })
      }

      captchaApi.getGtCaptcha(this.uuidCode)
        .then(function (res) {
          init(res.data);
        });
    },

  }
}

